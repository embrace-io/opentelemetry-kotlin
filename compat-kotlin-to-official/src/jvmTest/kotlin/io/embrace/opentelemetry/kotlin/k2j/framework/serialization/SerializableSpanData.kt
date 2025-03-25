package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.EventData
import io.opentelemetry.sdk.trace.data.LinkData
import io.opentelemetry.sdk.trace.data.SpanData

internal fun SpanData.toSerializable() = SerializableSpanData(
    name = name,
    kind = kind.name,
    statusData = status.toSerializable(),
    spanContext = spanContext.toSerializable(),
    parentSpanContext = parentSpanContext.toSerializable(),
    startTimestampNs = startEpochNanos,
    attributes = attributes.toSerializable(),
    events = events.map(EventData::toSerializable),
    links = links.map(LinkData::toSerializable),
    endTimestampNs = endEpochNanos,
    ended = hasEnded(),
    totalRecordedEvents = totalRecordedEvents,
    totalRecordedLinks = totalRecordedLinks,
    totalAttributeCount = totalAttributeCount,
    resource = resource.toSerializable(),
)
