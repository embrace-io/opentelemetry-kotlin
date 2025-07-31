package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

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
