package io.embrace.opentelemetry.kotlin.fibonacci

import kotlin.test.Test
import kotlin.test.assertEquals

internal class JvmFibiTest {

    @Test
    fun `test 3rd element`() {
        assertEquals(5, generateFibi().take(3).last())
    }
}
