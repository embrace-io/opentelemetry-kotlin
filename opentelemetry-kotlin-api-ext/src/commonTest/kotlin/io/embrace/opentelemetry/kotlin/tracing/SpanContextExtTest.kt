package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanContextExtTest {

    @Test
    fun testSpanIdBytes() {
        val spanContext = FakeSpanContext.INVALID
        val expected = spanContext.spanId
        val observed = spanContext.spanIdBytes.decodeToString()
        assertEquals(expected, observed)
    }

    @Test
    fun testTraceIdBytes() {
        val spanContext = FakeSpanContext.INVALID
        val expected = spanContext.traceId
        val observed = spanContext.traceIdBytes.decodeToString()
        assertEquals(expected, observed)
    }
}
