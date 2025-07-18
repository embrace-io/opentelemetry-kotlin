package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Contains details about the trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spancontext
 */
@ThreadSafe
@ExperimentalApi
public interface TraceFlags {

    /**
     * True if the trace is sampled.
     */
    @ThreadSafe
    public val isSampled: Boolean

    /**
     * True if the trace is random.
     */
    @ThreadSafe
    public val isRandom: Boolean

    /**
     * Returns the hexadecimal representation of the trace flags.
     */
    @ThreadSafe
    public val hex: String

    public companion object
}
