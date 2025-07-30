package io.embrace.opentelemetry.kotlin.j2k.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLoggerProvider
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.k2j.framework.TestResourceConfig
import io.opentelemetry.api.logs.Severity
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class OtelJavaLogExportTest {

    private lateinit var harness: OtelKotlinHarness
    private lateinit var loggerProvider: OtelJavaLoggerProvider

    @BeforeTest
    fun setUp() {
        harness = OtelKotlinHarness()
        loggerProvider = harness.javaApi.logsBridge
    }

    @Test
    fun `test minimal log export`() {
        val logger = loggerProvider.get("my_logger")

        // logging without a body is allowed by the OTel spec, so we assert the MVP log here
        logger.logRecordBuilder().emit()

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_minimal.json",
        )
    }

    @Test
    fun `test log properties export`() {
        val logger = loggerProvider.loggerBuilder("my_logger")
            .setInstrumentationVersion("0.1.0")
            .setSchemaUrl("https://example.com/schema")
            .build()

        logger.logRecordBuilder()
            .setBody("Hello, world!")
            .setTimestamp(100L, TimeUnit.NANOSECONDS)
            .setObservedTimestamp(50L, TimeUnit.NANOSECONDS)
            .setSeverity(Severity.ERROR2)
            .setSeverityText("Error")
            .setAttribute("key2", "value2")
            .emit()

        harness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_props.json",
            sanitizeSpanContextIds = true,
        )
    }

    @Test
    fun `test java logger provider resource export`() {
        val resourceHarness = OtelKotlinHarness(
            resourceConfig = TestResourceConfig("https://example.com/some_schema.json") {
                setStringAttribute("service.name", "test-service")
                setStringAttribute("service.version", "1.0.0")
                setStringAttribute("environment", "test")
            }
        )

        val javaLogger = resourceHarness.javaApi.logsBridge.get("test_logger")
        javaLogger.logRecordBuilder().setBody("Test log with custom resource").emit()

        resourceHarness.assertLogRecords(
            expectedCount = 1,
            goldenFileName = "log_resource.json",
            sanitizeSpanContextIds = true,
        )
    }
}
