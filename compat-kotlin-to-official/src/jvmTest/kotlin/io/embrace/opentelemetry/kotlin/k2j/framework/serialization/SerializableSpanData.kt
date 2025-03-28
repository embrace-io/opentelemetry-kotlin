package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.EventData
import io.opentelemetry.sdk.trace.data.SpanData

internal fun SpanData.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableSpanData(
    name = name,
    kind = kind.name,
    statusData = status.toSerializable(),
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    parentSpanContext = parentSpanContext.toSerializable(sanitizeSpanContextIds),
    startTimestampNs = startEpochNanos,
    attributes = attributes.toSerializable(),
    events = events.map(EventData::toSerializable),
    links = links.map { it.toSerializable(sanitizeSpanContextIds) },
    endTimestampNs = endEpochNanos,
    ended = hasEnded(),
    totalRecordedEvents = totalRecordedEvents,
    totalRecordedLinks = totalRecordedLinks,
    totalAttributeCount = totalAttributeCount,
    resource = resource.toSerializable(),
)
