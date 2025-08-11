package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.creator.FakeObjectCreator
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
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
    private lateinit var objectCreator: ObjectCreator

    @BeforeTest
    fun setUp() {
        clock = FakeClock()
        processor = FakeSpanProcessor()
        objectCreator = FakeObjectCreator()
        tracer = TracerImpl(clock, processor, objectCreator, key)
    }

    @Test
    fun `test span end explicit timestamp`() {
        val timestamp = 100L
        val span = tracer.createSpan("test")
        span.end(timestamp)
        assertSpanTimestamp(timestamp)
    }

    @Test
    fun `test span end implicit timestamp`() {
        val timestamp = 50L
        clock.time = timestamp
        val span = tracer.createSpan("test")
        span.end()
        assertSpanTimestamp(timestamp)
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

        assertSpanTimestamp(timestamp)
    }

    private fun assertSpanTimestamp(timestamp: Long) {
        val readableSpan = processor.startCalls.single()
        assertEquals(timestamp, readableSpan.endTimestamp)

        val endedSpan = processor.endCalls.single()
        assertEquals(timestamp, endedSpan.endTimestamp)
    }
}
