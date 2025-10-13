package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Defines a storage mechanism for the implicit Context.
 *
 * https://opentelemetry.io/docs/specs/otel/context/#optional-global-operations
 */
@ExperimentalApi
public interface ImplicitContextStorage {

    /**
     * Stores the supplied context as the current implicit context.
     */
    public fun setImplicitContext(context: Context)

    /**
     * Returns the current implicit context, or
     * [io.embrace.opentelemetry.kotlin.factory.ContextFactory.root] if none is set.
     */
    public fun implicitContext(): Context
}
