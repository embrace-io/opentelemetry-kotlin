@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.j2k.bridge.context.OtelJavaContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.invalid
import io.embrace.opentelemetry.kotlin.tracing.NonRecordingSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.api.trace.otelJavaSpanContextKey

private val invalidSpan = NonRecordingSpan(SpanContext.invalid(), SpanContext.invalid())

/**
 * Returns an invalid span.
 */
public fun Span.Companion.invalid(): Span = invalidSpan

/**
 * Returns a span from the supplied [SpanContext], or the return value of [Span.Companion.invalid]
 * if the [SpanContext] is invalid.
 */
public fun Span.Companion.fromSpanContext(spanContext: SpanContext): Span = when {
    spanContext.isValid -> NonRecordingSpan(SpanContext.invalid(), spanContext)
    else -> invalid()
}

/**
 * Returns a span from the supplied [Context], or the return value of [Span.Companion.invalid]
 * if the [Context] has no span.
 */
public fun Span.Companion.fromContext(context: Context): Span {
    val otelJavaCtx = (context as? OtelJavaContextAdapter)?.impl ?: OtelJavaContext.root()
    val span: OtelJavaSpan = otelJavaCtx.get(otelJavaSpanContextKey) ?: OtelJavaSpan.getInvalid()
    return NonRecordingSpan(SpanContext.invalid(), SpanContextAdapter(span.spanContext))
}

/**
 * Stores a span in the supplied [Context], returning the new context.
 */
public fun Span.storeInContext(context: Context): Context {
    val otelJavaCtx = (context as? OtelJavaContextAdapter)?.impl ?: OtelJavaContext.root()
    val otelJavaSpan = this as? SpanAdapter ?: OtelJavaSpan.getInvalid()
    return OtelJavaContextAdapter(otelJavaCtx.with(otelJavaSpan))
}
