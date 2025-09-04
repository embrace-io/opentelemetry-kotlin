package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfoImpl
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.factory.FakeSdkFactory
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SpanEndTest {

    private val key = InstrumentationScopeInfoImpl("key", null, null, emptyMap())
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeSpanProcessor
    private lateinit var sdkFactory: SdkFactory

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        sdkFactory = FakeSdkFactory()
        tracer = TracerImpl(
            clock,
            processor,
            sdkFactory,
            key,
            FakeResource(),
            fakeSpanLimitsConfig
        )
    }

    @Test
    fun testSpanEndWithExplicitTimestamp() {
        val timestamp = 100L
        val span = tracer.createSpan("test")
        span.end(timestamp)
        assertSpanTimestamp(timestamp)
    }

    @Test
    fun testSpanEndWithImplicitTimestamp() {
        val timestamp = 50L
        clock.time = timestamp
        val span = tracer.createSpan("test")
        span.end()
        assertSpanTimestamp(timestamp)
    }

    @Test
    fun testSpanIsRecording() {
        val span = tracer.createSpan("test")
        assertTrue(span.isRecording())
        span.end()
        assertFalse(span.isRecording())
    }

    @Test
    fun testMultipleEndCalls() {
        val span = tracer.createSpan("test")
        assertTrue(span.isRecording())

        val timestamp = 100L
        span.end(timestamp)
        assertFalse(span.isRecording())

        span.end(80)
        assertFalse(span.isRecording())

        span.end()
        assertFalse(span.isRecording())

        assertSpanTimestamp(timestamp)
    }

    @Test
    fun testSpanProcessorContainingEndCall() {
        var startCallCount = 0
        var endCallCount = 0

        processor.startAction = { rwSpan, _ ->
            startCallCount++
            assertTrue(rwSpan.isRecording())
            rwSpan.end()
        }
        processor.endAction = { rSpan ->
            endCallCount++
            assertTrue(rSpan.hasEnded)
        }

        val span = tracer.createSpan("test")
        assertFalse(span.isRecording())
        span.end()
        assertFalse(span.isRecording())

        assertEquals(1, startCallCount)
        assertEquals(1, endCallCount)
        assertSpanTimestamp(clock.now())
    }

    private fun assertSpanTimestamp(timestamp: Long) {
        val readableSpan = processor.startCalls.single()
        assertEquals(timestamp, readableSpan.endTimestamp)

        val endedSpan = processor.endCalls.single()
        assertEquals(timestamp, endedSpan.endTimestamp)
    }
}
