package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.tracing.model.Span

/**
 * A factory for retrieving [io.embrace.opentelemetry.kotlin.context.Context] instances.
 */
@ExperimentalApi
public interface ContextFactory {

    /**
     * Retrieves the root Context.
     */
    public fun root(): Context

    /**
     * Stores a span and returns a new [io.embrace.opentelemetry.kotlin.context.Context], using a pre-defined key.
     */
    public fun storeSpan(context: Context, span: Span): Context
}
