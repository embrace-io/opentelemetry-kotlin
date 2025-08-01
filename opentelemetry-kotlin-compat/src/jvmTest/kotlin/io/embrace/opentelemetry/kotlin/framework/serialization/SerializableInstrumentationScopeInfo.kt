package io.embrace.opentelemetry.kotlin.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableInstrumentationScopeInfo(
    val name: String,
    val version: String,
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
