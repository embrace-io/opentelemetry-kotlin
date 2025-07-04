package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableStatusCode(
    val description: String,
)
