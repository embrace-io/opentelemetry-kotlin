package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.opentelemetry.kotlin.clock.FakeClock
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.creator.createObjectCreator
import io.opentelemetry.kotlin.resource.FakeResource
import io.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import io.opentelemetry.kotlin.tracing.model.SpanContext
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TracerSpanContextTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeSpanProcessor
    private lateinit var objectCreator: ObjectCreator

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        objectCreator = createObjectCreator()
        tracer = TracerImpl(
            clock,
            processor,
            objectCreator,
            key,
            FakeResource(),
            fakeSpanLimitsConfig,
        )
    }

    @Test
    fun `test span with no explicit parent context`() {
        val span = tracer.createSpan("test")
        assertFalse(span.parent.isValid)
        val spanContext = span.spanContext
        assertValidSpanContext(spanContext)
    }

    @Test
    fun `test span with explicit parent context - invalid span`() {
        val invalidSpan = objectCreator.span.invalid
        assertFalse(invalidSpan.spanContext.isValid)
        val parentCtx = objectCreator.context.storeSpan(objectCreator.context.root(), invalidSpan)
        val span = tracer.createSpan("test", parentContext = parentCtx)

        assertFalse(span.parent.isValid)
        val spanContext = span.spanContext
        assertValidSpanContext(spanContext)
    }

    @Test
    fun `test span with explicit parent context - valid span`() {
        val parentSpan = tracer.createSpan("parent")
        val parentCtx = objectCreator.context.storeSpan(objectCreator.context.root(), parentSpan)
        val span = tracer.createSpan("test", parentContext = parentCtx)

        assertTrue(span.parent.isValid)
        val spanContext = span.spanContext
        assertValidSpanContext(spanContext)
        assertEquals(parentSpan.spanContext.traceId, spanContext.traceId)
        assertNotEquals(parentSpan.spanContext.spanId, spanContext.spanId)
    }

    private fun assertValidSpanContext(spanContext: SpanContext) {
        assertTrue(spanContext.isValid)
        assertFalse(spanContext.isRemote)
        assertNotEquals(objectCreator.idCreator.invalidTraceId, spanContext.traceId)
        assertNotEquals(objectCreator.idCreator.invalidSpanId, spanContext.spanId)
        assertEquals(emptyMap(), spanContext.traceState.asMap())
        assertEquals("01", spanContext.traceFlags.hex)
    }
}
