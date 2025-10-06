package io.embrace.opentelemetry.kotlin.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.FakeInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.export.conversion.toProtobuf
import kotlin.collections.get
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
class ScopeProtobufConversionTest {

    @Test
    fun testEmptyConversion() {
        val obj = FakeInstrumentationScopeInfo("name", null, null, emptyMap())
        val protobuf = obj.toProtobuf()
        assertEquals(0, protobuf.attributesCount)
        assertEquals(0, protobuf.attributesList.size)
        assertEquals(0, protobuf.droppedAttributesCount)
    }

    @Test
    fun testNonDefaultConversion() {
        val obj = FakeInstrumentationScopeInfo(
            "custom_name",
            "0.1.0",
            "https://example.com/schema",
            mapOf("foo" to "bar")
        )
        val protobuf = obj.toProtobuf()
        assertEquals(1, protobuf.attributesCount)
        assertEquals("foo", protobuf.attributesList[0].key)
        assertEquals("bar", protobuf.attributesList[0].value.stringValue)
        assertEquals(0, protobuf.droppedAttributesCount)
        assertEquals("custom_name", protobuf.name)
        assertEquals("0.1.0", protobuf.version)
    }
}
