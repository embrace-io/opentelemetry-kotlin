package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableEventData(
    val name: String,
    val attributes: Map<String, String>,
    val timestampNs: Long,
    val totalAttributesCount: Int,
)
