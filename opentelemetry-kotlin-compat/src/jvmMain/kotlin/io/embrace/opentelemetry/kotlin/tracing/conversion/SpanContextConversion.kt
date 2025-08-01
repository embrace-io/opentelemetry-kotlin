package io.embrace.opentelemetry.kotlin.tracing.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaImmutableSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
public fun SpanContext.toOtelJavaSpanContext(): OtelJavaSpanContext {
    return OtelJavaImmutableSpanContext.create(
        traceId,
        spanId,
        traceFlags.toOtelJavaTraceFlags(),
        traceState.toOtelJavaTraceState(),
        isRemote,
        false
    )
}
