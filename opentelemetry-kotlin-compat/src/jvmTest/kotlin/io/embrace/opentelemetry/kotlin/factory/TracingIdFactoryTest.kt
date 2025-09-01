package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class TracingIdFactoryTest {

    private val factory = createCompatSdkFactory().tracingIds

    @Test
    fun `test invalid`() {
        assertEquals("00000000000000000000000000000000", factory.invalidTraceId)
        assertEquals("0000000000000000", factory.invalidSpanId)
    }

    @Test
    fun `test trace ID generation`() {
        val traceId = factory.generateTraceId()
        assertEquals(32, traceId.length)
    }

    @Test
    fun `test span ID generation`() {
        val spanId = factory.generateSpanId()
        assertEquals(16, spanId.length)
    }
}
