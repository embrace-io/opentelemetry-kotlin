package io.embrace.opentelemetry.kotlin.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableSpanStatusData(
    val name: String,
    val description: String,
)
