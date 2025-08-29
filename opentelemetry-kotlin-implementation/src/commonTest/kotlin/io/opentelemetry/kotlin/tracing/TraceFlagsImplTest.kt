package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.creator.TraceFlagsCreatorImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TraceFlagsImplTest {

    private val creator = TraceFlagsCreatorImpl()

    @Test
    fun `fromHex returns Default TraceFlags on an empty string`() {
        val flags = creator.fromHex("")

        assertFalse(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("00", flags.hex)
    }

    @Test
    fun `fromHex returns Default TraceFlags on a single character`() {
        val flags = creator.fromHex("1")

        assertFalse(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("00", flags.hex)
    }

    @Test
    fun `fromHex returns correct TraceFlags`() {
        val default = creator.fromHex("00")
        assertFalse(default.isRandom)
        assertFalse(default.isSampled)
        assertEquals("00", default.hex)

        val sampled = creator.fromHex("01")
        assertFalse(sampled.isRandom)
        assertTrue(sampled.isSampled)
        assertEquals("01", sampled.hex)

        val random = creator.fromHex("02")
        assertTrue(random.isRandom)
        assertFalse(random.isSampled)
        assertEquals("02", random.hex)

        val sampledAndRandom = creator.fromHex("03")
        assertTrue(sampledAndRandom.isRandom)
        assertTrue(sampledAndRandom.isSampled)
        assertEquals("03", sampledAndRandom.hex)
    }
}
