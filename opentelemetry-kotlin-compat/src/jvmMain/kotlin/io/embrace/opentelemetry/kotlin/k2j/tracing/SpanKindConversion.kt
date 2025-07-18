package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal fun SpanKind.convertToOtelJava(): OtelJavaSpanKind = when (this) {
    SpanKind.INTERNAL -> OtelJavaSpanKind.INTERNAL
    SpanKind.CLIENT -> OtelJavaSpanKind.CLIENT
    SpanKind.SERVER -> OtelJavaSpanKind.SERVER
    SpanKind.PRODUCER -> OtelJavaSpanKind.PRODUCER
    SpanKind.CONSUMER -> OtelJavaSpanKind.CONSUMER
}
