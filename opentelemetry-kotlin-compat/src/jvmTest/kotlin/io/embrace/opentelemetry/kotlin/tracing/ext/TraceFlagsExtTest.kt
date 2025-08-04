package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeTraceFlags
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalApi::class)
internal class TraceFlagsExtTest {

    @Test
    fun toOtelJavaTraceFlags() {
        val expected = FakeTraceFlags()
        val observed = expected.toOtelJavaTraceFlags()
        assertEquals(expected.hex, observed.asHex())
    }
}
