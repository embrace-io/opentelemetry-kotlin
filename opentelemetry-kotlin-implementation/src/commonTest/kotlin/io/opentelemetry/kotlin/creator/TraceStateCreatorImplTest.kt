package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class TraceStateCreatorImplTest {

    private val creator = TraceStateCreatorImpl()

    @Test
    fun `default property returns empty TraceState`() {
        val traceState = creator.default

        assertNull(traceState.get("any-key"))
        assertTrue(traceState.asMap().isEmpty())
    }
}
