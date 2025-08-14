package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.tracing.model.Span

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
