package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
data class SerializableInstrumentationScopeInfo(
    val name: String,
    val version: String,
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
