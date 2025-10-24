package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
class ResourceProtobufConversionTest {

    @Test
    fun testEmptyConversion() {
        val obj = FakeResource(attributes = emptyMap())
        val protobuf = obj.toProtobuf()
        assertEquals(0, protobuf.attributes.size)
        assertEquals(0, protobuf.dropped_attributes_count)
    }

    @Test
    fun testNonDefaultConversion() {
        val obj = FakeResource(
            attributes = mapOf(
                "string" to "foo"
            )
        )
        val protobuf = obj.toProtobuf()
        assertEquals(1, protobuf.attributes.size)
        assertEquals("foo", protobuf.attributes[0].value_?.string_value)
        assertEquals(0, protobuf.dropped_attributes_count)
    }
}