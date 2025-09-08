package io.embrace.opentelemetry.kotlin

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SyncTest {

    @Test
    fun test() {
        val expected = "value"
        val observed = sync(this) {
            expected
        }
        assertEquals(expected, observed)
    }

    @Test
    fun `test order`() {
        val latch = CountDownLatch(1)
        val values = mutableListOf<Int>()

        sync(this) {
            latch.await(10, TimeUnit.MILLISECONDS)
            values.add(1)
        }
        sync(this) {
            values.add(2)
        }
        assertEquals(listOf(1, 2), values)
    }
}
