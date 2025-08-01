package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags

/**
 * A factory for creating [TraceFlags] instances.
 */
@ExperimentalApi
public interface TraceFlagsCreator {

    /**
     * Retrieves the default TraceFlags implementation.
     */
    public val default: TraceFlags
}
