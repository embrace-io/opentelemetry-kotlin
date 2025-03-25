package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
data class SerializableSpanStatusData(
    val code: Int,
    val description: String,
)
