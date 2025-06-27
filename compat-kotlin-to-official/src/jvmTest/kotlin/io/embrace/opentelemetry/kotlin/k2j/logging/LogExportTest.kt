package io.embrace.opentelemetry.kotlin.k2j.logging

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class LogExportTest {

    private lateinit var harness: OtelKotlinHarness
    private lateinit var loggerProvider: LoggerProvider

    @BeforeTest
    fun setUp() {
        harness = OtelKotlinHarness()
        loggerProvider = LoggerProviderAdapter(harness.sdk.logsBridge)
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
            timestampNs = 100L,
            observedTimestampNs = 50L,
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
}
