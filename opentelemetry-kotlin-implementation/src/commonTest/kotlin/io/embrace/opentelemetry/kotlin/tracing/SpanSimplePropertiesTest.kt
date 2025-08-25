package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanSimplePropertiesTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        tracer = TracerImpl(
            clock,
            FakeSpanProcessor(),
            FakeObjectCreator(),
            key,
            FakeResource(),
            fakeSpanLimitsConfig
        )
    }

    @Test
    fun `test span name`() {
        val name = "test"
        val span = tracer.createSpan(name)
        assertEquals(name, span.name)
    }

    @Test
    fun `test span name override`() {
        val span = tracer.createSpan("test")
        val override = "another"
        span.name = override
        assertEquals(override, span.name)
    }

    @Test
    fun `test span name after end`() {
        val name = "test"
        val span = tracer.createSpan(name)
        span.end()
        span.name = "another"
        assertEquals(name, span.name)
    }

    @Test
    fun `test span status`() {
        val span = tracer.createSpan("test")
        assertEquals(StatusData.Unset, span.status)
    }

    @Test
    fun `test span status override`() {
        val span = tracer.createSpan("test")
        span.status = StatusData.Ok
        assertEquals(StatusData.Ok, span.status)
    }

    @Test
    fun `test span status after end`() {
        val span = tracer.createSpan("test")
        span.end()
        span.status = StatusData.Ok
        assertEquals(StatusData.Unset, span.status)
    }

    @Test
    fun `test span kind default`() {
        val span = tracer.createSpan("test")
        assertEquals(SpanKind.INTERNAL, span.spanKind)
    }

    @Test
    fun `test span kind override `() {
        val span = tracer.createSpan("test", spanKind = SpanKind.CLIENT)
        assertEquals(SpanKind.CLIENT, span.spanKind)
    }

    @Test
    fun `test span start timestamp default`() {
        clock.time = 5
        val span = tracer.createSpan("test")
        assertEquals(clock.time, span.startTimestamp)
    }

    @Test
    fun `test span start timestamp explicit`() {
        val now = 9L
        val span = tracer.createSpan("test", startTimestamp = now)
        assertEquals(now, span.startTimestamp)
    }
}
