package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.k2j.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.SpanKind

internal fun SpanKind.convertToOtelJava(): OtelJavaSpanKind = when (this) {
    SpanKind.INTERNAL -> OtelJavaSpanKind.INTERNAL
    SpanKind.CLIENT -> OtelJavaSpanKind.CLIENT
    SpanKind.SERVER -> OtelJavaSpanKind.SERVER
    SpanKind.PRODUCER -> OtelJavaSpanKind.PRODUCER
    SpanKind.CONSUMER -> OtelJavaSpanKind.CONSUMER
}
