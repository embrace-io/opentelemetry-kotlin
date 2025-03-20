package io.embrace.opentelemetry.kotlin.tracing

/**
 * Contains details about the trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spancontext
 */
public interface TraceFlags {

    /**
     * True if the trace is sampled.
     */
    public val isSampled: Boolean
}
