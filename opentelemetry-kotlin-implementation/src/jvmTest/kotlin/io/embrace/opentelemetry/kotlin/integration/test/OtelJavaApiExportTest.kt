package io.embrace.opentelemetry.kotlin.integration.test

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
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
    fun testComplexSpanExport() {
        val javaApi = harness.kotlinApi.toOtelJavaApi()
        val tracer = javaApi.tracerProvider.get("tracer")
        val spanA = tracer.spanBuilder("span_a").startSpan().apply {
            end()
        }
        val spanB = tracer.spanBuilder("span_b").startSpan().apply {
            end()
        }

        tracer.spanBuilder("my_span")
            .setAttribute("key", "value")
            .setAttribute("long", 5L)
            .setAttribute("double", 3.5)
            .setAttribute("boolean", false)
            .setAttribute(OtelJavaAttributeKey.stringKey("string"), "string")
            .setNoParent()
            .addLink(spanA.spanContext)
            .addLink(spanB.spanContext, OtelJavaAttributes.empty())
            .startSpan()
            .end()
        harness.assertSpans(3, "span_complex_java_api.json")
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
