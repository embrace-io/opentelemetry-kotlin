package io.embrace.opentelemetry.kotlin.fibonacci

import io.embrace.opentelemetry.kotlin.fibonacci.firstElement
import io.embrace.opentelemetry.kotlin.fibonacci.generateFibi
import io.embrace.opentelemetry.kotlin.fibonacci.secondElement
import kotlin.test.Test
import kotlin.test.assertEquals

internal class FibiTest {

    @Test
    fun `test 3rd element`() {
        assertEquals(firstElement + secondElement, generateFibi().take(3).last())
    }
}