package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.j2k.OtelKotlinSpanKind
import io.opentelemetry.api.trace.SpanKind

internal fun SpanKind.convertToOtelKotlin(): OtelKotlinSpanKind = when (this) {
    SpanKind.INTERNAL -> OtelKotlinSpanKind.INTERNAL
    SpanKind.CLIENT -> OtelKotlinSpanKind.CLIENT
    SpanKind.SERVER -> OtelKotlinSpanKind.SERVER
    SpanKind.PRODUCER -> OtelKotlinSpanKind.PRODUCER
    SpanKind.CONSUMER -> OtelKotlinSpanKind.CONSUMER
}
