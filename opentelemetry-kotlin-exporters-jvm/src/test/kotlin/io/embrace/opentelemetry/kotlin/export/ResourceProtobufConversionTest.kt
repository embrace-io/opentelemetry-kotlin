package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.toProtobuf
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import kotlin.collections.get
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
class ResourceProtobufConversionTest {

    @Test
    fun testEmptyConversion() {
        val obj = FakeResource(attributes = emptyMap())
        val protobuf = obj.toProtobuf()
        assertEquals(0, protobuf.attributesCount)
        assertEquals(0, protobuf.attributesList.size)
        assertEquals(0, protobuf.droppedAttributesCount)
    }

    @Test
    fun testNonDefaultConversion() {
        val obj = FakeResource(
            attributes = mapOf(
                "string" to "foo"
            )
        )
        val protobuf = obj.toProtobuf()
        assertEquals(1, protobuf.attributesCount)
        assertEquals("foo", protobuf.attributesList[0].value.stringValue)
        assertEquals(0, protobuf.droppedAttributesCount)
    }
}
