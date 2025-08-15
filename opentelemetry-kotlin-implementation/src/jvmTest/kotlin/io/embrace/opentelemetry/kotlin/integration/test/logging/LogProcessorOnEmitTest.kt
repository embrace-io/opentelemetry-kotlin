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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class LogProcessorOnEmitTest {

    private lateinit var harness: IntegrationTestHarness

    @BeforeTest
    fun setUp() {
        harness = IntegrationTestHarness()
    }

    @Test
    fun `test override properties in LogRecordProcessor onEmit`() {
        prepareConfig()
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
        harness.assertLogRecords(1, "log_emit_override.json")
    }

    private fun prepareConfig() {
        harness.config.attributes = {
            setStringAttribute("resource.foo", "bar")
        }
        harness.config.schemaUrl = "https://example.com/foo"
        harness.config.logRecordProcessors.add(OnEmitLogRecordProcessor())
    }

    private fun prepareContext(): Context {
        val span = harness.tracer.createSpan("span")
        val contextCreator = harness.objectCreator.context
        val ctx = contextCreator.storeSpan(contextCreator.root(), span)
        return ctx
    }

    private class OnEmitLogRecordProcessor : LogRecordProcessor {
        override fun onEmit(
            log: ReadWriteLogRecord,
            context: Context
        ) {
            log.assertAttributes()
            log.overrideAttributes()
        }

        private fun ReadWriteLogRecord.assertAttributes() {
            assertEquals("custom_log", body)
            assertEquals(500, timestamp)
            assertEquals(600, observedTimestamp)
            assertEquals(SeverityNumber.WARN2, severityNumber)
            assertEquals("warn2", severityText)
            assertEquals("bar", attributes["foo"])
            assertEquals(true, attributes["experiment_enabled"])
            assertEquals("test_logger", instrumentationScopeInfo.name)
            assertEquals("bar", resource.attributes["resource.foo"])
            assertTrue(spanContext.isValid)
        }

        private fun ReadWriteLogRecord.overrideAttributes() {
            body = "override"
            timestamp = 123
            observedTimestamp = 456
            severityNumber = SeverityNumber.INFO
            severityText = "info"
            setStringAttribute("key", "value")
        }

        override fun forceFlush(): OperationResultCode = OperationResultCode.Success
        override fun shutdown(): OperationResultCode = OperationResultCode.Success
    }
}
