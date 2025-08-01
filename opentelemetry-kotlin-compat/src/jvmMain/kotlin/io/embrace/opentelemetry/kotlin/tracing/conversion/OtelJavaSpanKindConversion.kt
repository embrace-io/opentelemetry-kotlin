package io.embrace.opentelemetry.kotlin.tracing.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal fun OtelJavaSpanKind.toOtelKotlinSpanKind(): SpanKind = when (this) {
    OtelJavaSpanKind.INTERNAL -> SpanKind.INTERNAL
    OtelJavaSpanKind.CLIENT -> SpanKind.CLIENT
    OtelJavaSpanKind.SERVER -> SpanKind.SERVER
    OtelJavaSpanKind.PRODUCER -> SpanKind.PRODUCER
    OtelJavaSpanKind.CONSUMER -> SpanKind.CONSUMER
}
