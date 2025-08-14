package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
fun SpanContext.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableSpanContext(
    traceId = if (sanitizeSpanContextIds) {
        "0".repeat(32)
    } else {
        traceId
    },
    spanId = if (sanitizeSpanContextIds) {
        "0".repeat(16)
    } else {
        spanId
    },
    traceFlags = traceFlags.hex,
    traceState = traceState.asMap(),
)
