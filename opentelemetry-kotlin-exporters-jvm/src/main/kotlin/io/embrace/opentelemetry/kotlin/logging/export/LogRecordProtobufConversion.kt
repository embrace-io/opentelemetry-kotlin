package io.embrace.opentelemetry.kotlin.logging.export

import com.google.protobuf.ByteString
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.createKeyValues
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.proto.common.v1.anyValue
import io.opentelemetry.proto.logs.v1.LogRecord
import io.opentelemetry.proto.logs.v1.logRecord

@OptIn(ExperimentalApi::class)
fun ReadableLogRecord.toProtobuf(): LogRecord {
    val record = this
    return logRecord {
        traceId = ByteString.copyFrom(record.spanContext.traceIdBytes)
        spanId = ByteString.copyFrom(record.spanContext.spanIdBytes)

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
        attributes.addAll(record.attributes.createKeyValues())
    }
}
