package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeTraceState
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class TraceStateExtTest {

    @Test
    fun toOtelJavaTraceState() {
        val expected = FakeTraceState()
        val observed = expected.toOtelJavaTraceState()
        assertEquals(expected.asMap(), observed.asMap())
    }
}
