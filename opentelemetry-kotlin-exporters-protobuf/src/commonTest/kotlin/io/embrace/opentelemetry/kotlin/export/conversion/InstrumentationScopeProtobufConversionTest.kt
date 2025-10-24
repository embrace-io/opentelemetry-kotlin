package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.FakeInstrumentationScopeInfo
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
class InstrumentationScopeProtobufConversionTest {

    @Test
    fun testEmptyConversion() {
        val obj = FakeInstrumentationScopeInfo("name", null, null, emptyMap())
        val protobuf = obj.toProtobuf()
        assertEquals(0, protobuf.attributes.size)
        assertEquals("name", protobuf.name)
        assertEquals("", protobuf.version)
    }

    @Test
    fun testConversionWithValues() {
        val obj = FakeInstrumentationScopeInfo(
            "custom_name",
            "0.1.0",
            "https://example.com/schema",
            mapOf("foo" to "bar")
        )
        val protobuf = obj.toProtobuf()
        assertEquals("custom_name", protobuf.name)
        assertEquals("0.1.0", protobuf.version)
        assertEquals(1, protobuf.attributes.size)
        val attribute = protobuf.attributes[0]
        assertEquals("foo", attribute.key)
        assertEquals("bar", attribute.value_?.string_value)
    }
}