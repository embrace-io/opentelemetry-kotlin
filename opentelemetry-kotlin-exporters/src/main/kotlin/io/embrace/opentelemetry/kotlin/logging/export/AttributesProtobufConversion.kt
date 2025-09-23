package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.proto.common.v1.AnyValueKt
import io.opentelemetry.proto.common.v1.KeyValue
import io.opentelemetry.proto.common.v1.anyValue
import io.opentelemetry.proto.common.v1.keyValue

@OptIn(ExperimentalApi::class)
fun Map<String, Any>.createKeyValues(): List<KeyValue> {
    return map {
        keyValue {
            key = it.key
            value = anyValue {
                convertAttributeValue(it.value)
            }
        }
    }
}

private fun AnyValueKt.Dsl.convertAttributeValue(value: Any) {
    when (value) {
        is String -> stringValue = value
        is Long -> intValue = value
        is Double -> doubleValue = value
        is Boolean -> boolValue = value
        else -> throw UnsupportedOperationException()
    }
}
