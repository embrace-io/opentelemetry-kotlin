package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.EventData
import io.opentelemetry.sdk.trace.data.LinkData
import io.opentelemetry.sdk.trace.data.SpanData
import kotlinx.serialization.Serializable

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

@Serializable
internal data class SerializableSpanData(
    val name: String,
    val kind: String,
    val statusData: SerializableSpanStatusData,
    val spanContext: SerializableSpanContext,
    val parentSpanContext: SerializableSpanContext,
    val startTimestampNs: Long,
    val attributes: Map<String, String>,
    val events: List<SerializableEventData>,
    val links: List<SerializableLinkData>,
    val endTimestampNs: Long,
    val ended: Boolean,
    val totalRecordedEvents: Int,
    val totalRecordedLinks: Int,
    val totalAttributeCount: Int,
    val resource: SerializableResource,
)
