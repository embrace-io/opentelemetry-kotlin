package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class TracingIdCreatorImplTest {

    private val creator = createCompatObjectCreator().idCreator

    @Test
    fun `test invalid`() {
        assertEquals("00000000000000000000000000000000", creator.invalidTraceId)
        assertEquals("0000000000000000", creator.invalidSpanId)
    }

    @Test
    fun `test trace ID generation`() {
        val traceId = creator.generateTraceId()
        assertEquals(32, traceId.length)
    }

    @Test
    fun `test span ID generation`() {
        val spanId = creator.generateSpanId()
        assertEquals(16, spanId.length)
    }
}
