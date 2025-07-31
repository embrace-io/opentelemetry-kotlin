package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context

/**
 * A factory for retrieving [Context] instances.
 */
@ExperimentalApi
public interface ContextCreator {

    /**
     * Retrieves the root Context.
     */
    public fun root(): Context
}
