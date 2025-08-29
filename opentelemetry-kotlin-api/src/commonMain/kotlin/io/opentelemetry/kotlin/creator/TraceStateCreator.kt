package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.model.TraceState

/**
 * A factory for creating [TraceState] instances.
 */
@ExperimentalApi
public interface TraceStateCreator {

    /**
     * Retrieves the default TraceState implementation.
     */
    public val default: TraceState
}
