package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.conversion.createKeyValues
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord
import io.opentelemetry.proto.common.v1.AnyValue
import io.opentelemetry.proto.logs.v1.LogRecord
import io.opentelemetry.proto.logs.v1.SeverityNumber.SEVERITY_NUMBER_UNSPECIFIED
import okio.ByteString.Companion.toByteString


@OptIn(ExperimentalApi::class)
internal fun ReadableLogRecord.toProtobuf(): LogRecord = LogRecord(
    trace_id = spanContext.traceIdBytes.toByteString(),
    span_id = spanContext.spanIdBytes.toByteString(),
    time_unix_nano = timestamp ?: 0L,
    observed_time_unix_nano = observedTimestamp ?: 0L,
    severity_number = severityNumber?.convertSeverityNumber() ?: SEVERITY_NUMBER_UNSPECIFIED,
    severity_text = severityText ?: "",
    body = body?.let { AnyValue(string_value = it) },
    attributes = attributes.createKeyValues(),
)
