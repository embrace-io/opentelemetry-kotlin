package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableSpanContext

internal fun OtelJavaSpanContext.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableSpanContext(
    traceId = when {
        sanitizeSpanContextIds -> OtelJavaSpanContext.getInvalid().traceId
        else -> traceId
    },
    spanId = when {
        sanitizeSpanContextIds -> OtelJavaSpanContext.getInvalid().spanId
        else -> spanId
    },
    traceFlags = traceFlags.asHex(),
    traceState = traceState.asMap(),
)
