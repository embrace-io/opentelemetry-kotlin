package io.opentelemetry.kotlin.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
data class SerializableSpanData(
    val name: String,
    val kind: String,
    val statusData: io.opentelemetry.kotlin.framework.serialization.SerializableSpanStatusData,
    val spanContext: io.opentelemetry.kotlin.framework.serialization.SerializableSpanContext,
    val parentSpanContext: io.opentelemetry.kotlin.framework.serialization.SerializableSpanContext,
    val startTimestamp: Long,
    val attributes: Map<String, String>,
    val events: List<io.opentelemetry.kotlin.framework.serialization.SerializableEventData>,
    val links: List<io.opentelemetry.kotlin.framework.serialization.SerializableLinkData>,
    val endTimestamp: Long,
    val ended: Boolean,
    val totalRecordedEvents: Int,
    val totalRecordedLinks: Int,
    val totalAttributeCount: Int,
    val resource: io.opentelemetry.kotlin.framework.serialization.SerializableResource,
    val instrumentationScopeInfo: io.opentelemetry.kotlin.framework.serialization.SerializableInstrumentationScopeInfo
)
