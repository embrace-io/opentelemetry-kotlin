package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.EventData
import kotlinx.serialization.Serializable

internal fun EventData.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toSerializable(),
    timestampNs = epochNanos,
    totalAttributesCount = attributes.size(),
)

@Serializable
internal data class SerializableEventData(
    val name: String,
    val attributes: Map<String, String>,
    val timestampNs: Long,
    val totalAttributesCount: Int,
)
