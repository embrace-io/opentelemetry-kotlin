package io.embrace.opentelemetry.kotlin.integration.test.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.integration.test.IntegrationTestHarness
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class LogProcessorNaughtyExportTest {

    private lateinit var harness: IntegrationTestHarness

    @BeforeTest
    fun setUp() {
        harness = IntegrationTestHarness()
    }

    @Test
    fun `test override properties in LogRecordProcessor onEmit`() {
        val processor = NaughtyLogRecordProcessor()
        prepareConfig(processor)
        val ctx = prepareContext()

        harness.logger.log(
            body = "custom_log",
            timestamp = 500,
            observedTimestamp = 600,
            severityNumber = SeverityNumber.WARN2,
            severityText = "warn2",
            context = ctx,
        ) {
            setStringAttribute("foo", "bar")
            setBooleanAttribute("experiment_enabled", true)
        }
        harness.assertLogRecords(1, "log_naughty_export.json") {
            processor.overrideAttributes()
        }
    }

    private fun prepareConfig(processor: LogRecordProcessor) {
        harness.config.attributes = {
            setStringAttribute("resource.foo", "bar")
        }
        harness.config.schemaUrl = "https://example.com/foo"
        harness.config.logRecordProcessors.add(processor)
    }

    private fun prepareContext(): Context {
        val span = harness.tracer.createSpan("span")
        val contextFactory = harness.sdkFactory.context
        val ctx = contextFactory.storeSpan(contextFactory.root(), span)
        return ctx
    }

    private class NaughtyLogRecordProcessor : LogRecordProcessor {

        private lateinit var log: ReadWriteLogRecord

        override fun onEmit(
            log: ReadWriteLogRecord,
            context: Context
        ) {
            this.log = log
        }

        fun overrideAttributes() {
            with(log) {
                body = "override"
                timestamp = 123
                observedTimestamp = 456
                severityNumber = SeverityNumber.INFO
                severityText = "info"
                setStringAttribute("key", "value")
            }
        }

        override fun forceFlush(): OperationResultCode = OperationResultCode.Success
        override fun shutdown(): OperationResultCode = OperationResultCode.Success
    }
}
