package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TraceStateCreatorImplTest {

    private val creator = TraceStateCreatorImpl()

    @Test
    fun testDefaultTraceState() {
        val traceState = creator.default
        assertNull(traceState.get("any-key"))
        assertTrue(traceState.asMap().isEmpty())
    }
}
