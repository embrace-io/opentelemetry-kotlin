package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeTraceState
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanContext
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.create
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.default
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.invalid
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
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
        tracerProvider = harness.kotlinApi.tracerProvider
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

        assertEquals(SpanKind.CLIENT, span.spanKind)
        assertEquals(500, span.startTimestamp)
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
        val customTraceState = FakeTraceState(
            mapOf(
                "vendor1" to "value1",
                "vendor2" to "value2"
            )
        )

        // trace state is propagated to children, so we can test it up with a test parent.
        val customSpanContext = SpanContext.create(
            traceId = "12345678901234567890123456789012",
            spanId = "1234567890123456",
            traceFlags = TraceFlags.default(),
            traceState = customTraceState
        )
        val span = tracer.createSpan("trace_state_test", parent = customSpanContext)
        val state = span.spanContext.traceState

        // Test asMap()
        val expectedMap = mapOf("vendor1" to "value1", "vendor2" to "value2")
        assertEquals(expectedMap, state.asMap())

        // Test get() method
        assertEquals("value1", state.get("vendor1"))
        assertEquals("value2", state.get("vendor2"))
        assertNull(state.get("nonexistent"))
    }

    @Test
    fun `test invalid span context`() {
        val invalidContext = SpanContext.invalid()

        // Test invalid context properties
        assertFalse(invalidContext.isValid)
        assertEquals("00000000000000000000000000000000", invalidContext.traceId)
        assertEquals("0000000000000000", invalidContext.spanId)

        // Test span creation with invalid parent
        val span = tracer.createSpan("test_span", parent = invalidContext)

        // Child span should be created with a valid context
        assertTrue(span.spanContext.isValid)
        assertNotEquals(invalidContext.traceId, span.spanContext.traceId)
        assertNotEquals(invalidContext.spanId, span.spanContext.spanId)

        span.end()
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

    @Test
    fun `test tracer with schema url and attributes`() {
        val schemaUrl = "https://opentelemetry.io/schemas/1.21.0"
        val tracerWithSchemaUrl = tracerProvider.getTracer(
            name = "test-tracer",
            version = "2.0.0",
            schemaUrl = schemaUrl
        ) {
            setStringAttribute("tracer_attr", "tracer_value")
            setLongAttribute("tracer_id", 123)
        }

        val span = tracerWithSchemaUrl.createSpan("schema_url_span")
        span.end()

        harness.assertSpans(expectedCount = 1, goldenFileName = "span_schema_url.json")
    }

    @Test
    fun `test multiple operations`() {
        // create multiple spans, with multiple links and events
        val linkedSpan1 = tracer.createSpan("linked_span_1")
        val linkedSpan2 = tracer.createSpan("linked_span_2")
        val linkedSpan3 = tracer.createSpan("linked_span_3")

        val span = tracer.createSpan("multi_operations_span").apply {
            // Add multiple events
            addEvent("event_1", 100L)
            addEvent("event_2", 200L) {
                setStringAttribute("event_attr", "value")
            }
            addEvent("event_3", 300L)

            // Add multiple links
            addLink(linkedSpan1.spanContext) {}
            addLink(linkedSpan2.spanContext) {
                setStringAttribute("link_attr", "link_value")
            }
            addLink(linkedSpan3.spanContext) {}

            // Verify counts
            assertEquals(3, events().size)
            assertEquals(3, links().size)
        }

        span.end()
        linkedSpan1.end()
        linkedSpan2.end()
        linkedSpan3.end()

        harness.assertSpans(expectedCount = 4, goldenFileName = "span_multiple_operations.json")
    }

    @Test
    fun `test attributes edge cases`() {
        val span = tracer.createSpan("edge_case_attributes").apply {
            // Test empty string
            setStringAttribute("empty_string", "")

            // Test empty lists
            setStringListAttribute("empty_string_list", emptyList())
            setBooleanListAttribute("empty_bool_list", emptyList())
            setLongListAttribute("empty_long_list", emptyList())
            setDoubleListAttribute("empty_double_list", emptyList())

            // Test whitespace
            setStringAttribute("whitespace_only", " ")

            // Test lists with empty elements
            setStringListAttribute("list_with_empty", listOf("", "non-empty", "", "another-value"))
        }

        span.end()

        harness.assertSpans(expectedCount = 1, goldenFileName = "span_edge_case_attributes.json")
    }

    @Test
    fun `test trace and span id validation without sanitization`() {
        val span1 = tracer.createSpan("validation_span_1")
        val span2 = tracer.createSpan("validation_span_2")
        val span3 = tracer.createSpan("validation_span_3", parent = span1.spanContext)

        span1.end()
        span2.end()
        span3.end()

        harness.assertSpans(3, null, false) { spans ->
            val validationSpan1 = spans.first { it.name == "validation_span_1" }
            val validationSpan2 = spans.first { it.name == "validation_span_2" }
            val validationSpan3 = spans.first { it.name == "validation_span_3" }

            // Validate ID formats and hex characters
            listOf(validationSpan1, validationSpan2, validationSpan3).forEach {
                it.spanContext.assertValidIds()
            }

            // Trace IDs should be the same for a parent-child set of spans. Otherwise, they should be different.
            assertEquals(validationSpan1.spanContext.traceId, validationSpan3.spanContext.traceId)
            assertNotEquals(validationSpan1.spanContext.traceId, validationSpan2.spanContext.traceId)

            // All span IDs should be unique
            val spanIds =
                setOf(validationSpan1.spanContext.spanId, validationSpan2.spanContext.spanId, validationSpan3.spanContext.spanId)
            assertEquals(3, spanIds.size)

            // Root spans have invalid parents
            listOf(validationSpan1, validationSpan2).forEach {
                assertEquals("00000000000000000000000000000000", it.parentSpanContext.traceId)
            }
        }
    }

    // IDs should be valid hex strings with correct OpenTelemetry lengths (trace: 32 chars, span: 16 chars)
    private fun SerializableSpanContext.assertValidIds() {
        assertEquals(32, traceId.length)
        assertEquals(16, spanId.length)
        val hexPattern = Regex("[0-9a-f]+")
        assertTrue(traceId.matches(hexPattern))
        assertTrue(spanId.matches(hexPattern))
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
