@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanAdapter

/**
 * Stores a span in the supplied [Context], returning the new context.
 */
public fun Span.storeInContext(context: Context): Context {
    val otelJavaCtx = (context as? ContextAdapter)?.impl ?: OtelJavaContext.root()
    val otelJavaSpan = this as? SpanAdapter ?: OtelJavaSpan.getInvalid()
    return ContextAdapter(otelJavaCtx.with(otelJavaSpan))
}
