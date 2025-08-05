package io.embrace.opentelemetry.kotlin.attributes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class MutableAttributeContainerImplTest {

    @Test
    fun `test attributes`() {
        val attrs = MutableAttributeContainerImpl().apply {
            setStringAttribute("string", "value")
            setBooleanAttribute("boolean", true)
            setDoubleAttribute("double", 5.2)
            setLongAttribute("long", 123L)
            setStringListAttribute("stringList", listOf("a", "b", "c"))
            setDoubleListAttribute("doubleList", listOf(1.5, 2.5, 3.5))
            setLongListAttribute("longList", listOf(4L, 5L, 6L))
            setBooleanListAttribute("booleanList", listOf(true, false, true))
        }.attributes

        val expected = mapOf(
            "string" to "value",
            "boolean" to true,
            "double" to 5.2,
            "long" to 123L,
            "stringList" to listOf("a", "b", "c"),
            "doubleList" to listOf(1.5, 2.5, 3.5),
            "longList" to listOf(4L, 5L, 6L),
            "booleanList" to listOf(true, false, true),
        )
        assertEquals(expected, attrs)
    }
}
