package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * A factory for creating Span instances.
 */
@ExperimentalApi
public interface SpanFactory {

    /**
     * An invalid span.
     */
    public val invalid: Span

    /**
     * Returns a span from the supplied [io.embrace.opentelemetry.kotlin.tracing.model.SpanContext].
     * If the span context has no span an invalid span
     * object will be returned.
     */
    public fun fromSpanContext(spanContext: SpanContext): Span

    /**
     * Returns a span from the supplied [io.embrace.opentelemetry.kotlin.context.Context].
     * If the context has no span an invalid span
     * object will be returned.
     */
    public fun fromContext(context: Context): Span
}
