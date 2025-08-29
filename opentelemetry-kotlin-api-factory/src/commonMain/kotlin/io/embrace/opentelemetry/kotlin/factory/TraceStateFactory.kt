package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

/**
 * A factory for creating [io.embrace.opentelemetry.kotlin.tracing.model.TraceState] instances.
 */
@ExperimentalApi
public interface TraceStateFactory {

    /**
     * Retrieves the default TraceState implementation.
     */
    public val default: TraceState
}
