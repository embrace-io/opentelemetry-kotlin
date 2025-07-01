package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableResource(
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
