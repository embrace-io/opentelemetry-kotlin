package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
data class SerializableResource(
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
