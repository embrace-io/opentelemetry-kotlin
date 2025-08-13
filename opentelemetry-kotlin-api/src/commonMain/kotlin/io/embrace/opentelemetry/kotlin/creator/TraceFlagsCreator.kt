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

    /**
     * Creates TraceFlags with the specified sampling and randomization behavior.
     */
    public fun create(sampled: Boolean, random: Boolean): TraceFlags

    /**
     * Creates TraceFlags from a hex string representation.
     * Returns the default TraceFlags implementation if the input is invalid.
     */
    public fun fromHex(hex: String): TraceFlags
}
