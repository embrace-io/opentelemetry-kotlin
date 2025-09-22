package io.embrace.opentelemetry.kotlin.logging.export

import com.google.protobuf.ByteString
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.proto.common.v1.AnyValueKt
import io.opentelemetry.proto.common.v1.anyValue
import io.opentelemetry.proto.common.v1.keyValue
import io.opentelemetry.proto.logs.v1.LogRecord
import io.opentelemetry.proto.logs.v1.logRecord

@OptIn(ExperimentalApi::class)
fun ReadableLogRecord.toProtobuf(): LogRecord {
    val record = this
    return logRecord {
        traceId = ByteString.copyFromUtf8(record.spanContext.traceId)
        spanId = ByteString.copyFromUtf8(record.spanContext.spanId)

        record.body?.let {
            body = anyValue {
                stringValue = it
            }
        }
        record.timestamp?.let {
            timeUnixNano = it
        }
        record.observedTimestamp?.let {
            observedTimeUnixNano = it
        }
        record.severityText?.let {
            severityText = it
        }
        record.severityNumber?.let {
            severityNumber = it.convertSeverityNumber()
        }
        record.attributes.forEach { entry ->
            attributes.add(keyValue {
                key = entry.key
                value = anyValue {
                    convertAttributeValue( entry.value)
                }
            })
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
