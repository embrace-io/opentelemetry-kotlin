package io.embrace.opentelemetry.kotlin.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class LogExportTest {

    private lateinit var harness: OtelKotlinHarness

    @BeforeTest
    fun setUp() {
        harness = OtelKotlinHarness()
    }

    @Test
    fun `test minimal log export`() {
        // logging without a body is allowed by the OTel spec, so we assert the MVP log here
        harness.logger.log()

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_minimal.json",
        )
    }

    @Test
    fun `test log properties export`() {
        val logger = harness.kotlinApi.loggerProvider.getLogger(
            name = "my_logger",
            version = "0.1.0",
            schemaUrl = "https://example.com/schema",
        ) {
            setStringAttribute("key1", "value1")
        }
        logger.log(
            body = "Hello, world!",
            timestamp = 100L,
            observedTimestamp = 50L,
            severityNumber = SeverityNumber.ERROR2,
            severityText = "Error",
        ) {
            setStringAttribute("key2", "value2")
        }

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_props.json",
        )
    }

    @Test
    fun `test logger provider resource export`() {
        harness.config.apply {
            schemaUrl = "https://example.com/some_schema.json"
            attributes = {
                setStringAttribute("service.name", "test-service")
                setStringAttribute("service.version", "1.0.0")
                setStringAttribute("environment", "test")
            }
        }

        val logger = harness.kotlinApi.loggerProvider.getLogger("test_logger")
        logger.log(body = "Test log with custom resource")

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_resource.json",
        )
    }

    @Test
    fun `test context is passed to processor`() {
        // Create a LogRecordProcessor that captures any passed Context.
        val contextCapturingProcessor = ContextCapturingProcessor()
        harness.config.logRecordProcessors.add(contextCapturingProcessor)

        // Create a context key and add a test value
        val currentContext = harness.kotlinApi.contextFactory.implicitContext()
        val contextKey = currentContext.createKey<String>("best_team")
        val testContextValue = "independiente"
        val testContext = currentContext.set(contextKey, testContextValue)

        // Log a message with the created context
        harness.logger.log(body = "Test log with context", context = testContext)

        // Verify context was captured and contains expected value
        val actualValue = contextCapturingProcessor.capturedContext?.get(contextKey)
        assertSame(testContextValue, actualValue)
    }

    @Test
    fun `test log limit export`() {
        harness.config.logLimits = {
            attributeCountLimit = 2
            attributeValueLengthLimit = 3
        }
        harness.logger.log(body = "Test log limits") {
            setStringAttribute("key1", "max")
            setStringAttribute("key2", "over_max")
            setStringAttribute("key3", "another")
        }
        harness.assertLogRecords(1, "log_limits.json")
    }

    @Test
    fun `test log export with custom processor`() {
        harness.config.logRecordProcessors.add(CustomLogRecordProcessor())
        harness.logger.log("Test")

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_custom_processor.json",
        )
    }

    @Test
    fun `test event export`() {
        val logger = harness.kotlinApi.loggerProvider.getLogger("test_logger")
        logger.logEvent(
            eventName = "my_event_name",
            body = "Some Event",
            severityNumber = SeverityNumber.WARN4
        ) {
            setStringAttribute("key1", "value1")
        }

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "event.json",
        )
    }

    /**
     * Custom processor that captures the context passed to onEmit
     */
    private class ContextCapturingProcessor : LogRecordProcessor {
        var capturedContext: Context? = null
            private set

        override fun onEmit(log: ReadWriteLogRecord, context: Context) {
            capturedContext = context
        }

        override fun shutdown(): OperationResultCode = OperationResultCode.Success
        override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    }

    /**
     * Custom processor that alters log records
     */
    private class CustomLogRecordProcessor : LogRecordProcessor {

        override fun onEmit(log: ReadWriteLogRecord, context: Context) {
            with(log) {
                timestamp = 5
                observedTimestamp = 10

                setStringAttribute("string", "value")
                setBooleanAttribute("bool", false)
                setDoubleAttribute("double", 5.4)
                setLongAttribute("long", 5L)
                setStringListAttribute("stringList", listOf("value"))
                setBooleanListAttribute("boolList", listOf(false))
                setDoubleListAttribute("doubleList", listOf(5.4))
                setLongListAttribute("longList", listOf(5L))

                // these cannot be set in OTel Java.
                severityNumber = SeverityNumber.ERROR2
                severityText = "bad_error"
                body = "altered"
            }
        }

        override fun shutdown(): OperationResultCode = OperationResultCode.Success
        override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    }
}
