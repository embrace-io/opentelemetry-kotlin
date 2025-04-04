package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.logs.data.LogRecordData

internal fun LogRecordData.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableLogRecordData(
    resource = resource.toSerializable(),
    instrumentationScopeInfo = instrumentationScopeInfo.toSerializable(),
    timestampEpochNanos = timestampEpochNanos,
    observedTimestampEpochNanos = observedTimestampEpochNanos,
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    severity = severity.name,
    severityText = severityText,
    body = bodyValue?.value as String?,
    attributes = attributes.toSerializable(),
    totalAttributeCount = totalAttributeCount,
)
