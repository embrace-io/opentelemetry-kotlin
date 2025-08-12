package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.provider.ApiProviderKey
import io.embrace.opentelemetry.kotlin.tracing.export.FakeSpanProcessor
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SpanEndTest {

    private val key = ApiProviderKey("key")
    private lateinit var tracer: TracerImpl
    private lateinit var clock: FakeClock
    private lateinit var processor: FakeSpanProcessor

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        tracer = TracerImpl(clock, processor, key)
    }

    @Test
    fun `test span end explicit timestamp`() {
        val timestamp = 100L
        val span = tracer.createSpan("test")
        span.end(timestamp)

        val endedSpan = processor.endCalls.single()
        assertEquals(timestamp, endedSpan.endTimestamp)
    }

    @Test
    fun `test span end implicit timestamp`() {
        val timestamp = 50L
        clock.time = timestamp
        val span = tracer.createSpan("test")
        span.end()

        val endedSpan = processor.endCalls.single()
        assertEquals(timestamp, endedSpan.endTimestamp)
    }

    @Test
    fun `test span isRecording`() {
        val span = tracer.createSpan("test")
        assertTrue(span.isRecording())
        span.end()
        assertFalse(span.isRecording())
    }

    @Test
    fun `test span multiple end calls`() {
        val span = tracer.createSpan("test")
        assertTrue(span.isRecording())

        val timestamp = 100L
        span.end(timestamp)
        assertFalse(span.isRecording())

        span.end(80)
        assertFalse(span.isRecording())

        span.end()
        assertFalse(span.isRecording())

        val endedSpan = processor.endCalls.single()
        assertEquals(timestamp, endedSpan.endTimestamp)
    }
}
