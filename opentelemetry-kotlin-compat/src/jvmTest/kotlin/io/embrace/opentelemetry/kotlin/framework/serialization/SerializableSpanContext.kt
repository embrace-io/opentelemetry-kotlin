package io.embrace.opentelemetry.kotlin.framework.serialization

import kotlinx.serialization.Serializable

@Serializable
internal data class SerializableSpanContext(
    val traceId: String,
    val spanId: String,
    val traceFlags: String,
    val traceState: Map<String, String>,
)
