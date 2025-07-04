package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal fun SpanContext.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableSpanContext(
    traceId = when {
        sanitizeSpanContextIds -> OtelJavaSpanContext.getInvalid().traceId
        else -> traceId
    },
    spanId = when {
        sanitizeSpanContextIds -> OtelJavaSpanContext.getInvalid().spanId
        else -> spanId
    },
    traceFlags = traceFlags.hex,
    traceState = traceState.asMap(),
)
