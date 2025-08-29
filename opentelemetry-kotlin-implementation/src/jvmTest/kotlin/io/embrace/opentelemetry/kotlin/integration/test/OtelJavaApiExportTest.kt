package io.embrace.opentelemetry.kotlin.integration.test

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSeverity
import io.embrace.opentelemetry.kotlin.toOtelJavaApi
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class OtelJavaApiExportTest {

    private lateinit var harness: IntegrationTestHarness

    @BeforeTest
    fun setUp() {
        harness = IntegrationTestHarness()
    }

    @Test
    fun testSimpleSpanExport() {
        val javaApi = harness.kotlinApi.toOtelJavaApi()
        val tracer = javaApi.tracerProvider.get("tracer")

        tracer.spanBuilder("my_span")
            .setAttribute("key", "value")
            .startSpan()
            .addEvent("my_event")
            .end()
        harness.assertSpans(1, "span_java_api.json")
    }

    @Test
    fun testSimpleLogExport() {
        val javaApi = harness.kotlinApi.toOtelJavaApi()
        val logger = javaApi.logsBridge.get("logger")

        logger.logRecordBuilder()
            .setBody("my_log")
            .setAttribute("key", "value")
            .setSeverity(OtelJavaSeverity.DEBUG2)
            .emit()
        harness.assertLogRecords(1, "log_java_api.json")
    }
}
