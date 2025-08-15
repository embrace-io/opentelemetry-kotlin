package io.embrace.opentelemetry.kotlin.clock

import kotlin.test.Test
import kotlin.test.assertTrue

internal class ClockImplTest {

    @Test
    fun `now returns positive nanosecond timestamp`() {
        val clock = ClockImpl()
        val time = clock.now()
        assertTrue(time > 0, "Clock should return positive nanosecond timestamp")
    }

    @Test
    fun `now returns realistic timestamp range`() {
        val clock = ClockImpl()
        val time = clock.now()
        
        // Should be roughly current time (after 2020, before 2100)
        val year2020Nanos = 1_577_836_800_000_000_000L // 2020-01-01 00:00:00 UTC
        val year2100Nanos = 4_102_444_800_000_000_000L // 2100-01-01 00:00:00 UTC
        
        assertTrue(time > year2020Nanos, "Timestamp should be after 2020")
        assertTrue(time < year2100Nanos, "Timestamp should be before 2100")
    }

    @Test
    fun `consecutive calls return non-decreasing values`() {
        val clock = ClockImpl()
        val times = mutableListOf<Long>()
        
        repeat(10) {
            times.add(clock.now())
        }
        
        // Check that timestamps are non-decreasing
        for (i in 1 until times.size) {
            assertTrue(times[i] >= times[i-1], "Consecutive timestamps should be non-decreasing")
        }
    }
}
