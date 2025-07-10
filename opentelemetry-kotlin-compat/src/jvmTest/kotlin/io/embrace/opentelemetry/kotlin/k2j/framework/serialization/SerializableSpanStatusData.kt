package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableSpanStatusData(
    val name: String,
    val description: String,
)
