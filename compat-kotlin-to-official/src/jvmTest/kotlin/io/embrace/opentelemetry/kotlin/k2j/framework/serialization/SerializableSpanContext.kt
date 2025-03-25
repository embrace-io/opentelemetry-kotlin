package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.api.trace.SpanContext
import kotlinx.serialization.Serializable

internal fun SpanContext.toSerializable() = SerializableSpanContext(
    traceId = SpanContext.getInvalid().traceId, // set to 0 for predictable tests
    spanId = SpanContext.getInvalid().spanId, // set to 0 for predictable tests
    traceFlags = traceFlags.asHex(),
    traceState = traceState.asMap(),
)

@Serializable
internal data class SerializableSpanContext(
    val traceId: String,
    val spanId: String,
    val traceFlags: String,
    val traceState: Map<String, String>,
)
