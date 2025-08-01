package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class TraceFlagsCreatorImplTest {

    private val creator = createCompatObjectCreator().traceFlags

    @Test
    fun `test valid`() {
        assertEquals("00", creator.default.hex)
    }
}
