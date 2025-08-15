package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
fun SpanContext.toSerializable() = SerializableSpanContext(
    traceId = traceId,
    spanId = spanId,
    traceFlags = traceFlags.hex,
    traceState = traceState.asMap(),
)
