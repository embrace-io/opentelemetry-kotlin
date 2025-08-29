package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TracingIdCreatorImplTest {

    private companion object {
        private const val SPAN_ID_PATTERN = "^[0-9a-f]{16}$"
        private const val TRACE_ID_PATTERN = "^[0-9a-f]{32}$"
    }

    private lateinit var creator: TracingIdCreator

    @BeforeTest
    fun setUp() {
        creator = TracingIdCreatorImpl(Random(0))
    }

    @Test
    fun `test invalid id creation`() {
        assertEquals("00000000000000000000000000000000", creator.invalidTraceId)
        assertEquals("0000000000000000", creator.invalidSpanId)
    }

    @Test
    fun `spanId has correct length and hex format`() {
        val spanId = creator.generateSpanId()
        assertEquals(16, spanId.length)
        assertTrue(spanId.matches(SPAN_ID_PATTERN.toRegex()))
    }

    @Test
    fun `traceId has correct length and hex format`() {
        val traceId = creator.generateTraceId()
        assertEquals(32, traceId.length)
        assertTrue(traceId.matches(TRACE_ID_PATTERN.toRegex()))
    }

    @Test
    fun `distinct trace IDs are generated`() {
        assertEquals(
            "2cc2b48c50aefe53b3974ed91e6b4ea9",
            creator.generateTraceId()
        )
        assertEquals(
            "e77bcc2f537f0b02efe86030ac2c3153",
            creator.generateTraceId()
        )
        assertEquals(
            "c0a79602f9a51310c8eed9889d46ef3b",
            creator.generateTraceId()
        )
    }

    @Test
    fun `distinct span IDs are generated`() {
        assertEquals("2cc2b48c50aefe53", creator.generateSpanId())
        assertEquals("1e6b4ea924f9baa8", creator.generateSpanId())
        assertEquals("537f0b02efe86030", creator.generateSpanId())
    }
}
