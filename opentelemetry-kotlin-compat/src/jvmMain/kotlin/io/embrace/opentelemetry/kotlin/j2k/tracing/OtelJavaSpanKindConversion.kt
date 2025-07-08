package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

public fun OtelJavaSpanKind.convertToOtelKotlin(): SpanKind = when (this) {
    OtelJavaSpanKind.INTERNAL -> SpanKind.INTERNAL
    OtelJavaSpanKind.CLIENT -> SpanKind.CLIENT
    OtelJavaSpanKind.SERVER -> SpanKind.SERVER
    OtelJavaSpanKind.PRODUCER -> SpanKind.PRODUCER
    OtelJavaSpanKind.CONSUMER -> SpanKind.CONSUMER
}
