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
    fun testSpanName() {
        val name = "test"
        val span = tracer.createSpan(name)
        assertEquals(name, span.name)
    }

    @Test
    fun testSpanNameOverride() {
        val span = tracer.createSpan("test")
        val override = "another"
        span.name = override
        assertEquals(override, span.name)
    }

    @Test
    fun testSpanNameAfterEnd() {
        val name = "test"
        val span = tracer.createSpan(name)
        span.end()
        span.name = "another"
        assertEquals(name, span.name)
    }

    @Test
    fun testSpanStatus() {
        val span = tracer.createSpan("test")
        assertEquals(StatusData.Unset, span.status)
    }

    @Test
    fun testSpanStatusOverride() {
        val span = tracer.createSpan("test")
        span.status = StatusData.Ok
        assertEquals(StatusData.Ok, span.status)
    }

    @Test
    fun testSpanStatusAfterEnd() {
        val span = tracer.createSpan("test")
        span.end()
        span.status = StatusData.Ok
        assertEquals(StatusData.Unset, span.status)
    }

    @Test
    fun testSpanKind() {
        val span = tracer.createSpan("test")
        assertEquals(SpanKind.INTERNAL, span.spanKind)
    }

    @Test
    fun testSpanKindOverride() {
        val span = tracer.createSpan("test", spanKind = SpanKind.CLIENT)
        assertEquals(SpanKind.CLIENT, span.spanKind)
    }

    @Test
    fun testSpanStartTimestamp() {
        clock.time = 5
        val span = tracer.createSpan("test")
        assertEquals(clock.time, span.startTimestamp)
    }

    @Test
    fun testSpanStartTimestampExplicit() {
        val now = 9L
        val span = tracer.createSpan("test", startTimestamp = now)
        assertEquals(now, span.startTimestamp)
    }
}
