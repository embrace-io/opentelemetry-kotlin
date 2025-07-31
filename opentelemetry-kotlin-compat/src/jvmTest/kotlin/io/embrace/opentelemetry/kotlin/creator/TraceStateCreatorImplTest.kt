package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class TraceStateCreatorImplTest {

    private val creator = createCompatObjectCreator().traceState

    @Test
    fun `test valid`() {
        assertEquals(emptyMap(), creator.default.asMap())
    }
}
