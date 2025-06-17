package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.toSerializable
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SpanExportTest {

    private lateinit var harness: OtelKotlinHarness
    private lateinit var tracerProvider: TracerProvider
    private lateinit var tracer: Tracer

    @BeforeTest
    fun setUp() {
        harness = OtelKotlinHarness()
        tracerProvider = TracerProviderAdapter(harness.sdk.tracerProvider, harness.clock)
        tracer = tracerProvider.getTracer("name", "version")
    }

    @Test
    fun `test minimal span export`() {
        val spanName = "my_span"
        tracer.createSpan(spanName).end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_minimal.json",
        )
    }

    @Test
    fun `test span properties export`() {
        val spanName = "my_span"
        val span = tracer.createSpan(
            name = spanName,
            spanKind = SpanKind.CLIENT,
            startTimestamp = 500
        )
        assertEquals(spanName, span.name)

        val name = "new_name"
        span.name = name
        assertEquals(name, span.name)

        assertEquals(StatusCode.Unset, span.status)
        span.status = StatusCode.Ok
        assertEquals(StatusCode.Ok, span.status)

        assertTrue(span.isRecording())
        span.end(1000)
        assertFalse(span.isRecording())

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_props.json",
        )
    }

    @Test
    fun `test span attributes export`() {
        val spanName = "span_attrs"
        val span = tracer.createSpan(spanName)
        span.assertAttributes()
        span.end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_attrs.json",
        )
    }

    @Test
    fun `test span events export`() {
        val spanName = "span_events"
        val span = tracer.createSpan(spanName).apply {
            assertTrue(events().isEmpty())

            val eventName = "my_event"
            val eventTimestamp = 150L
            addEvent(eventName, eventTimestamp) {
                assertAttributes()
            }

            val event = events().single()
            assertEquals(eventName, event.name)
            assertEquals(eventTimestamp, event.timestamp)
        }
        span.end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_events.json",
        )
    }

    @Test
    fun `test span context parent`() {
        val a = tracer.createSpan("a")
        val b = tracer.createSpan("b", parent = a.spanContext)
        val c = tracer.createSpan("c", parent = b.spanContext)

        assertNull(a.parent)
        assertNotNull(a.spanContext)
        assertEquals(a.spanContext, b.parent)
        assertEquals(b.spanContext, c.parent)
        assertNotNull(c.spanContext)

        a.end()
        b.end()
        c.end()

        harness.assertSpans(3, null, false) { spans ->
            val exportA = spans[0]
            val exportB = spans[1]
            val exportC = spans[2]

            assertEquals(OtelJavaSpanContext.getInvalid().toSerializable(false), exportA.parentSpanContext)
            assertNotNull(exportA.spanContext)
            assertEquals(exportA.spanContext, exportB.parentSpanContext)
            assertEquals(exportB.spanContext, exportC.parentSpanContext)
            assertNotNull(exportC.spanContext)
        }
    }

    @Test
    fun `test span trace flags`() {
        val span = tracer.createSpan("my_span")
        val flags = span.spanContext.traceFlags
        assertEquals("01", flags.convertToOtelJava().asHex())
        assertTrue(flags.isSampled)
        assertFalse(flags.isRandom)
    }

    @Test
    fun `test span trace state`() {
        val span = tracer.createSpan("my_span")
        val state = span.spanContext.traceState
        assertEquals(emptyMap(), state.asMap())
    }

    @Test
    fun `test span links export`() {
        val linkedSpan = tracer.createSpan("linked_span")
        val span = tracer.createSpan("span_links").apply {
            assertTrue(events().isEmpty())

            addLink(linkedSpan.spanContext) {
                assertAttributes()
            }

            val link = links().single()
            assertEquals(linkedSpan.spanContext, link.spanContext)
        }
        span.end()
        linkedSpan.end()

        harness.assertSpans(
            expectedCount = 2,
            goldenFileName = "span_links.json",
        )
    }

    private fun AttributeContainer.assertAttributes() {
        assertTrue(attributes().isEmpty())

        // set attributes
        setStringAttribute("string_key", "value")
        setStringAttribute("string_key", "second_value")
        setBooleanAttribute("bool_key", true)
        setLongAttribute("long_key", 42)
        setDoubleAttribute("double_key", 3.14)
        setStringListAttribute("string_list_key", listOf("a"))
        setBooleanListAttribute("bool_list_key", listOf(true))
        setLongListAttribute("long_list_key", listOf(42))
        setDoubleListAttribute("double_list_key", listOf(3.14))

        val observed = attributes()
        val expected = mapOf(
            "string_key" to "second_value",
            "bool_key" to true,
            "long_key" to 42L,
            "double_key" to 3.14,
            "string_list_key" to listOf("a"),
            "bool_list_key" to listOf(true),
            "long_list_key" to listOf(42L),
            "double_list_key" to listOf(3.14),
        )
        assertEquals(expected.size, observed.size)
        expected.forEach { entry ->
            assertEquals(entry.value, observed[entry.key])
        }
    }
}
