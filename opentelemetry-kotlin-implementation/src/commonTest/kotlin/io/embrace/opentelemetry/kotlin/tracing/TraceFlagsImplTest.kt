package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.creator.TraceFlagsCreatorImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TraceFlagsImplTest {

    private val creator = TraceFlagsCreatorImpl()

    @Test
    fun testEmptyString() {
        val flags = creator.fromHex("")

        assertFalse(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("00", flags.hex)
    }

    @Test
    fun testSingleChar() {
        val flags = creator.fromHex("1")

        assertFalse(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("00", flags.hex)
    }

    @Test
    fun testValidTraceFlagsParsed() {
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
