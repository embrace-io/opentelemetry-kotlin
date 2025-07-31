package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
public fun OtelJavaSpanKind.toOtelKotlin(): SpanKind = when (this) {
    OtelJavaSpanKind.INTERNAL -> SpanKind.INTERNAL
    OtelJavaSpanKind.CLIENT -> SpanKind.CLIENT
    OtelJavaSpanKind.SERVER -> SpanKind.SERVER
    OtelJavaSpanKind.PRODUCER -> SpanKind.PRODUCER
    OtelJavaSpanKind.CONSUMER -> SpanKind.CONSUMER
}
