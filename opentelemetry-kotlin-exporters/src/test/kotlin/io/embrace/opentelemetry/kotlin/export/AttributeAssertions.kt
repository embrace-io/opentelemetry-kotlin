package io.embrace.opentelemetry.kotlin.export

import io.opentelemetry.proto.common.v1.AnyValue
import io.opentelemetry.proto.common.v1.KeyValue
import kotlin.test.assertEquals

fun assertAttributesMatch(
    attributes: Map<String, Any>,
    protobuf: List<KeyValue>,
) {
    assertEquals(attributes.size, protobuf.size)
    protobuf.forEach { attr ->
        val expected = attributes[attr.key]
        val observed = retrieveValue(attr)
        assertEquals(expected, observed)
    }
}

private fun retrieveValue(attr: KeyValue): Any {
    val obj = attr.value
    return when {
        obj.hasStringValue() -> obj.stringValue
        obj.hasIntValue() -> obj.intValue
        obj.hasDoubleValue() -> obj.doubleValue
        obj.hasBoolValue() -> obj.boolValue
        obj.hasArrayValue() -> retrieveListValue(obj)
        else -> error("Unknown type!")
    }
}

private fun retrieveListValue(obj: AnyValue): Any {
    return obj.arrayValue.valuesList.map { entry: AnyValue ->
        when {
            entry.hasStringValue() -> entry.stringValue
            entry.hasIntValue() -> entry.intValue
            entry.hasDoubleValue() -> entry.doubleValue
            entry.hasBoolValue() -> entry.boolValue
            else -> error("Unknown type!")
        }
    }
}
