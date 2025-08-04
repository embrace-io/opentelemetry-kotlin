package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SpanContextExtTest {

    @Test
    fun `test span context spanIdBytes extension`() {
        val spanContext = FakeSpanContext()
        val expected = spanContext.spanId
        val observed = spanContext.spanIdBytes.decodeToString()
        assertEquals(expected, observed)
    }

    @Test
    fun `test span context traceIdBytes extension`() {
        val spanContext = FakeSpanContext()
        val expected = spanContext.traceId
        val observed = spanContext.traceIdBytes.decodeToString()
        assertEquals(expected, observed)
    }
}
