package io.embrace.opentelemetry.kotlin.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableEventData(
    val name: String,
    val attributes: Map<String, String>,
    val timestamp: Long,
    val totalAttributesCount: Int,
)
