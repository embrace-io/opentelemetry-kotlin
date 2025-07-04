package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent

@OptIn(ExperimentalApi::class)
internal fun ReadableSpan.toSerializable(sanitizeSpanContextIds: Boolean) =
    SerializableReadableSpan(
        name = name,
        kind = spanKind.name,
        statusData = status.toSerializable(),
        spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
        parentSpanContext = checkNotNull(parent).toSerializable(sanitizeSpanContextIds),
        startTimestampNs = startTimestamp,
        attributes = attributes.toStringMap(),
        events = events.map(ReadableSpanEvent::toSerializable),
        links = links.map { it.toSerializable(sanitizeSpanContextIds) },
        endTimestampNs = endTimestamp ?: -1,
        ended = hasEnded(),
        totalRecordedEvents = events.size,
        totalRecordedLinks = links.size,
        totalAttributeCount = attributes.size,
        resource = resource.toSerializable(),
    )
