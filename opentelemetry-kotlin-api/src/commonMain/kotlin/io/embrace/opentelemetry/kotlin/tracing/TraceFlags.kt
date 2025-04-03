package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Contains details about the trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spancontext
 */
@ThreadSafe
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
}
