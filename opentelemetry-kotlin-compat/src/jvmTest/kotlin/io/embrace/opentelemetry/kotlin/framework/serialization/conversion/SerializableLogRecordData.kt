package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogRecordData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableLogRecordData

internal fun OtelJavaLogRecordData.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableLogRecordData(
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
