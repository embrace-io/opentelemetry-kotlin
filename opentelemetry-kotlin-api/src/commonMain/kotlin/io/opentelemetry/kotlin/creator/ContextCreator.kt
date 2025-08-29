package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.tracing.model.Span

/**
 * A factory for retrieving [Context] instances.
 */
@ExperimentalApi
public interface ContextCreator {

    /**
     * Retrieves the root Context.
     */
    public fun root(): Context

    /**
     * Stores a span and returns a new [Context], using a pre-defined key.
     */
    public fun storeSpan(context: Context, span: Span): Context
}
