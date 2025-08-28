package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TraceFlagsCreatorImplTest {

    private val creator = TraceFlagsCreatorImpl()

    @Test
    fun testDefaultProperty() {
        val flags = creator.default

        assertTrue(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("01", flags.hex)
    }

    @Test
    fun testSampledOnly() {
        val flags = creator.create(sampled = true, random = false)

        assertTrue(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("01", flags.hex)
    }

    @Test
    fun testRandomOnly() {
        val flags = creator.create(sampled = false, random = true)

        assertFalse(flags.isSampled)
        assertTrue(flags.isRandom)
        assertEquals("02", flags.hex)
    }

    @Test
    fun testSampledAndRandom() {
        val flags = creator.create(sampled = true, random = true)

        assertTrue(flags.isSampled)
        assertTrue(flags.isRandom)
        assertEquals("03", flags.hex)
    }

    @Test
    fun testDefault() {
        val flags = creator.create(sampled = false, random = false)

        assertFalse(flags.isSampled)
        assertFalse(flags.isRandom)
        assertEquals("00", flags.hex)
    }

    @Test
    fun testFromHexWithValidStrings() {
        val default = creator.fromHex("00")
        assertFalse(default.isSampled)
        assertFalse(default.isRandom)
        assertEquals("00", default.hex)

        val sampled = creator.fromHex("01")
        assertTrue(sampled.isSampled)
        assertFalse(sampled.isRandom)
        assertEquals("01", sampled.hex)

        val random = creator.fromHex("02")
        assertFalse(random.isSampled)
        assertTrue(random.isRandom)
        assertEquals("02", random.hex)

        val both = creator.fromHex("03")
        assertTrue(both.isSampled)
        assertTrue(both.isRandom)
        assertEquals("03", both.hex)

        val anotherBoth = creator.fromHex("2f") // 00101111
        assertTrue(anotherBoth.isSampled)
        assertTrue(anotherBoth.isRandom)
        assertEquals("03", anotherBoth.hex)
    }

    @Test
    fun testFromHexWithInvalidStrings() {
        val emptyString = creator.fromHex("")
        assertFalse(emptyString.isSampled)
        assertFalse(emptyString.isRandom)
        assertEquals("00", emptyString.hex)

        val shortString = creator.fromHex("1")
        assertFalse(shortString.isSampled)
        assertFalse(shortString.isRandom)
        assertEquals("00", shortString.hex)

        val notHex = creator.fromHex("2g")
        assertFalse(notHex.isSampled)
        assertFalse(notHex.isRandom)
        assertEquals("00", notHex.hex)
    }
}
