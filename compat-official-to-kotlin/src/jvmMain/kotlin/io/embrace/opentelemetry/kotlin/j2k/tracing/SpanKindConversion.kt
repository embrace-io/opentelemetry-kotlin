package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.opentelemetry.api.trace.SpanKind

internal fun SpanKind.convertToOtelKotlin(): io.embrace.opentelemetry.kotlin.tracing.SpanKind = when (this) {
    SpanKind.INTERNAL -> io.embrace.opentelemetry.kotlin.tracing.SpanKind.INTERNAL
    SpanKind.CLIENT -> io.embrace.opentelemetry.kotlin.tracing.SpanKind.CLIENT
    SpanKind.SERVER -> io.embrace.opentelemetry.kotlin.tracing.SpanKind.SERVER
    SpanKind.PRODUCER -> io.embrace.opentelemetry.kotlin.tracing.SpanKind.PRODUCER
    SpanKind.CONSUMER -> io.embrace.opentelemetry.kotlin.tracing.SpanKind.CONSUMER
}
