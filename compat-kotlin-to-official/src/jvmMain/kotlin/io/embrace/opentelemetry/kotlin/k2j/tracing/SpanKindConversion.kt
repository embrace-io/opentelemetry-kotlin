package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.SpanKind

internal fun SpanKind.convertToOtelJava(): io.opentelemetry.api.trace.SpanKind = when (this) {
    SpanKind.INTERNAL -> io.opentelemetry.api.trace.SpanKind.INTERNAL
    SpanKind.CLIENT -> io.opentelemetry.api.trace.SpanKind.CLIENT
    SpanKind.SERVER -> io.opentelemetry.api.trace.SpanKind.SERVER
    SpanKind.PRODUCER -> io.opentelemetry.api.trace.SpanKind.PRODUCER
    SpanKind.CONSUMER -> io.opentelemetry.api.trace.SpanKind.CONSUMER
}
