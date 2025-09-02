package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextStorageMode
import io.embrace.opentelemetry.kotlin.tracing.model.Span

/**
 * A factory for retrieving [Context] instances.
 */
@ExperimentalApi
public interface ContextFactory {

    /**
     * Retrieves the root Context.
     */
    public fun root(): Context

    /**
     * Retrieves the current Context from the given storage method. If no Context has been set
     * for this storage method, the root Context is returned.
     */
    public fun current(storageMode: ContextStorageMode = ContextStorageMode.THREAD_LOCAL): Context

    /**
     * Stores a span and returns a new [Context], using a pre-defined key.
     */
    public fun storeSpan(context: Context, span: Span): Context
}
