package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.convertAttributeValue
import io.opentelemetry.proto.common.v1.AnyValue
import io.opentelemetry.proto.common.v1.AnyValueKt
import io.opentelemetry.proto.common.v1.ArrayValue
import io.opentelemetry.proto.common.v1.KeyValue
import io.opentelemetry.proto.common.v1.anyValue
import io.opentelemetry.proto.common.v1.arrayValue
import io.opentelemetry.proto.common.v1.keyValue

@OptIn(ExperimentalApi::class)
fun Map<String, Any>.createKeyValues(): List<KeyValue> = map(::createKeyValue)

private fun createKeyValue(entry: Map.Entry<String, Any>): KeyValue = keyValue {
    key = entry.key
    value = createAnyValue(entry.value)
}

@Suppress("UNCHECKED_CAST")
private fun AnyValueKt.Dsl.convertAttributeValue(value: Any) {
    when (value) {
        is String -> stringValue = value
        is Long -> intValue = value
        is Double -> doubleValue = value
        is Boolean -> boolValue = value
        is List<*> -> arrayValue = handleList(value as List<Any>)
        else -> throw UnsupportedOperationException()
    }
}

private fun handleList(elements: List<Any>): ArrayValue = arrayValue {
    values.addAll(elements.map(::createAnyValue))
}

private fun createAnyValue(element: Any): AnyValue = anyValue {
    convertAttributeValue(element)
}
