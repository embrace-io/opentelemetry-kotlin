package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class TraceStateFactoryTest {

    private val factory = createCompatSdkFactory().traceState

    @Test
    fun `test valid`() {
        assertEquals(emptyMap(), factory.default.asMap())
    }
}
