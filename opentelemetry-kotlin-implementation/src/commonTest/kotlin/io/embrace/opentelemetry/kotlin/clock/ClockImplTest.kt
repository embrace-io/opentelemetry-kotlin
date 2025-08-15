package io.embrace.opentelemetry.kotlin.clock

import kotlin.test.Test
import kotlin.test.assertTrue

internal class ClockImplTest {

    @Test
    fun `now returns positive time value`() {
        val clock = ClockImpl()
        val time = clock.now()
        assertTrue(time > 0, "Clock should return positive time value")
    }

    @Test
    fun `now returns increasing values`() {
        val clock = ClockImpl()
        val time1 = clock.now()
        val time2 = clock.now()
        assertTrue(time2 >= time1, "Clock should return non-decreasing values")
    }
}
