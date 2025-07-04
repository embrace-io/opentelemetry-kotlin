package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableReadableSpan(
    val name: String,
    val kind: String,
    val statusData: SerializableStatusCode,
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
