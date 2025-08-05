package io.embrace.opentelemetry.kotlin

import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.ConcurrentHashMap

internal class ThreadSafeMapTest {

    @Test
    fun `test threadSafeMap`() {
        assertTrue(threadSafeMap<String, String>() is ConcurrentHashMap)
    }
}
