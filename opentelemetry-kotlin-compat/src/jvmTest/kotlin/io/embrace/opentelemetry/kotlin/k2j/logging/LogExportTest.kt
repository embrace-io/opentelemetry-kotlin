package io.embrace.opentelemetry.kotlin.k2j.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetryInstance
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeClock
import io.embrace.opentelemetry.kotlin.k2j.context.current
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.k2j.framework.TestResourceConfig
import io.embrace.opentelemetry.kotlin.kotlinApi
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.export.LogRecordProcessor
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class LogExportTest {

    private lateinit var harness: OtelKotlinHarness
    private lateinit var loggerProvider: LoggerProvider

    @BeforeTest
    fun setUp() {
        harness = OtelKotlinHarness()
        loggerProvider = harness.kotlinApi.loggerProvider
    }

    @Test
    fun `test minimal log export`() {
        val logger = loggerProvider.getLogger("my_logger")

        // logging without a body is allowed by the OTel spec, so we assert the MVP log here
        logger.log()

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_minimal.json",
            sanitizeSpanContextIds = true,
        )
    }

    @Test
    fun `test log properties export`() {
        val logger = loggerProvider.getLogger(
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
            sanitizeSpanContextIds = true,
        )
    }

    @Test
    fun `test context is passed to processor`() {
        // Create a LogRecordProcessor that captures any passed Context.
        val contextCapturingProcessor = ContextCapturingProcessor()

        // Use it on OpenTelemetryInstance creation.
        val kotlinApi = OpenTelemetryInstance.kotlinApi(
            clock = FakeClock(),
            loggerProvider = {
                addLogRecordProcessor(contextCapturingProcessor)
            }
        )

        // Create a context key and add a test value
        val rootContext = Context.Companion.current()
        val contextKey = rootContext.createKey<String>("best_team")
        val testContextValue = "independiente"
        val testContext = rootContext.set(contextKey, testContextValue)

        // Log a message with the created context
        val logger = kotlinApi.loggerProvider.getLogger("test_logger")
        logger.log(body = "Test log with context", context = testContext)

        // Verify context was captured and contains expected value
        val actualValue = contextCapturingProcessor.capturedContext?.get(contextKey)
        assertSame(testContextValue, actualValue)
    }

    @Test
    fun `test logger provider resource export`() {
        val harness = OtelKotlinHarness(
            resourceConfig = TestResourceConfig("https://example.com/some_schema.json") {
                setStringAttribute("service.name", "test-service")
                setStringAttribute("service.version", "1.0.0")
                setStringAttribute("environment", "test")
            }
        )

        val logger = harness.kotlinApi.loggerProvider.getLogger("test_logger")
        logger.log(body = "Test log with custom resource")

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_resource.json",
            sanitizeSpanContextIds = true,
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
}
