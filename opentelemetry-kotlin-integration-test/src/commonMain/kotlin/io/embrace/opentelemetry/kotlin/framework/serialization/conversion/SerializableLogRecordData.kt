package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableLogRecordData
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

@OptIn(ExperimentalApi::class)
fun ReadableLogRecord.toSerializable() = SerializableLogRecordData(
    resource = resource.toSerializable(),
    instrumentationScopeInfo = instrumentationScopeInfo.toSerializable(),
    timestampEpochNanos = timestamp ?: 0,
    observedTimestampEpochNanos = observedTimestamp ?: 0,
    spanContext = spanContext.toSerializable(),
    severity = severityNumber?.name.orEmpty(),
    severityText = severityText,
    body = body,
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size,
)
