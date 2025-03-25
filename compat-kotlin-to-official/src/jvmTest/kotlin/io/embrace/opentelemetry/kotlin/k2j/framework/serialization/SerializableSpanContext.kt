package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.api.trace.SpanContext

internal fun SpanContext.toSerializable() = SerializableSpanContext(
    traceId = SpanContext.getInvalid().traceId, // set to 0 for predictable tests
    spanId = SpanContext.getInvalid().spanId, // set to 0 for predictable tests
    traceFlags = traceFlags.asHex(),
    traceState = traceState.asMap(),
)
