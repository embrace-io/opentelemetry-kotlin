package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerProvider
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.opentelemetry.context.Context
import java.util.concurrent.TimeUnit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanExportTest {

    private lateinit var harness: OtelKotlinHarness
    private lateinit var tracerProvider: OtelJavaTracerProvider
    private lateinit var tracer: OtelJavaTracer

    @BeforeTest
    fun setUp() {
        harness = OtelKotlinHarness()
        tracerProvider = harness.javaApi.tracerProvider
        tracer = tracerProvider.get("name", "version")
    }

    @Test
    fun `test minimal span export`() {
        val spanName = "my_span"
        tracer.spanBuilder(spanName).startSpan().end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_minimal.json",
        )
    }

    @Test
    fun `test span properties export`() {
        val spanName = "my_span"
        val span = tracer.spanBuilder(spanName)
            .setSpanKind(OtelJavaSpanKind.CLIENT)
            .setStartTimestamp(500, TimeUnit.NANOSECONDS)
            .startSpan()

        span.updateName("new_name")
        span.setStatus(OtelJavaStatusCode.OK)

        assertTrue(span.isRecording)
        span.end(1000, TimeUnit.NANOSECONDS)
        assertFalse(span.isRecording)

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_props.json",
        )
    }

    @Test
    fun `test span attributes export`() {
        val spanName = "span_attrs"
        val span = tracer.spanBuilder(spanName).startSpan()
        span.setAllAttributes(attrs)
        span.end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_attrs.json",
        )
    }

    @Test
    fun `test span events export`() {
        val spanName = "span_events"
        val span = tracer.spanBuilder(spanName).startSpan().apply {
            val eventName = "my_event"
            val eventTimestamp = 150L
            addEvent(eventName, attrs, eventTimestamp, TimeUnit.NANOSECONDS)
        }
        span.end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_events.json",
        )
    }

    @Test
    fun `test span context parent`() {
        val a = tracer.spanBuilder("a").startSpan()
        val current = Context.current()
        val parentA = current.with(a)

        val b = tracer.spanBuilder("b").setParent(parentA).startSpan()
        val parentB = current.with(b)

        val c = tracer.spanBuilder("c").setParent(parentB).startSpan()
        a.end()
        b.end()
        c.end()

        harness.assertSpans(3, null, false) { spans ->
            val exportA = spans[0]
            val exportB = spans[1]
            val exportC = spans[2]

            assertEquals(
                OtelJavaSpanContext.getInvalid().toSerializable(false),
                exportA.parentSpanContext
            )
            assertNotNull(exportA.spanContext)
            assertEquals(exportA.spanContext, exportB.parentSpanContext)
            assertEquals(exportB.spanContext, exportC.parentSpanContext)
            assertNotNull(exportC.spanContext)
        }
    }

    @Test
    fun `test span trace flags`() {
        val span = tracer.spanBuilder("my_span").startSpan()
        val flags = span.spanContext.traceFlags
        assertEquals("01", flags.asHex())
        assertTrue(flags.isSampled)
    }

    @Test
    fun `test span trace state`() {
        val span = tracer.spanBuilder("my_span").startSpan()
        val state = span.spanContext.traceState
        assertEquals(emptyMap(), state.asMap())
    }

    @Test
    fun `test span links export`() {
        val linkedSpan = tracer.spanBuilder("linked_span").startSpan()
        val span = tracer.spanBuilder("span_links").startSpan().apply {
            addLink(linkedSpan.spanContext, attrs)
        }
        span.end()
        linkedSpan.end()

        harness.assertSpans(
            expectedCount = 2,
            goldenFileName = "span_links.json",
        )
    }

    private val attrs = OtelJavaAttributes.builder()
        .put("string_key", "value")
        .put("string_key", "second_value")
        .put("bool_key", true)
        .put("long_key", 42)
        .put("double_key", 3.14)
        .put(OtelJavaAttributeKey.stringArrayKey("string_list_key"), listOf("a"))
        .put(OtelJavaAttributeKey.booleanArrayKey("bool_list_key"), listOf(true))
        .put(OtelJavaAttributeKey.longArrayKey("long_list_key"), listOf(42))
        .put(OtelJavaAttributeKey.doubleArrayKey("double_list_key"), listOf(3.14))
        .build()
}
