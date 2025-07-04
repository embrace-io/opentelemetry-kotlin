package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableReadableLogRecord
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

@OptIn(ExperimentalApi::class)
internal fun ReadableLogRecord.toSerializable(sanitizeSpanContextIds: Boolean) =
    SerializableReadableLogRecord(
        resource = checkNotNull(resource).toSerializable(),
        instrumentationScopeInfo = checkNotNull(instrumentationScopeInfo).toSerializable(),
        timestampEpochNanos = timestamp ?: -1,
        observedTimestampEpochNanos = observedTimestamp ?: -1,
        spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
        severity = severityNumber?.name ?: "",
        severityText = severityText,
        body = body,
        attributes = attributes.toStringMap(),
        totalAttributeCount = attributes.size,
    )
