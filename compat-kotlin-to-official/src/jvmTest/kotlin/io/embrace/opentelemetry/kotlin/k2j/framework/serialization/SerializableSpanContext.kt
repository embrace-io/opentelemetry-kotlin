package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.api.trace.SpanContext

internal fun SpanContext.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableSpanContext(
    traceId = when {
        sanitizeSpanContextIds -> SpanContext.getInvalid().traceId
        else -> traceId
    },
    spanId = when {
        sanitizeSpanContextIds -> SpanContext.getInvalid().spanId
        else -> spanId
    },
    traceFlags = traceFlags.asHex(),
    traceState = traceState.asMap(),
)
