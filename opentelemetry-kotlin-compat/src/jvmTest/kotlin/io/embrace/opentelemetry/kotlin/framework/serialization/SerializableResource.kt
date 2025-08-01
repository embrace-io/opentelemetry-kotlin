package io.embrace.opentelemetry.kotlin.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableResource(
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
