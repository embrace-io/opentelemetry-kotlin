package io.embrace.opentelemetry.kotlin.tracing

/**
 * Represents the state of a Span that must be serialized & propagated along a distributed context.
 * This consists of a trace ID (a 16-byte array with at least one non-zero byte) and a span ID
 * (an 8-byte array with at least one non-zero byte).
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spancontext
 */
public interface SpanContext {

    /**
     * Hexadecimal representation of trace ID.
     */
    public val traceId: String

    /**
     * Hexadecimal representation of span ID.
     */
    public val spanId: String

    /**
     * Contains details about the trace.
     */
    public val traceFlags: TraceFlags

    /**
     * True if the SpanContext has a valid trace ID and span ID.
     */
    public val isValid: Boolean

    /**
     * True if the SpanContext was propagated from a remote parent.
     */
    public val isRemote: Boolean

    /**
     * Contains state about the trace.
     */
    public fun getTraceState(): TraceState

    /**
     * Mutates the trace state with the given action and replaces [traceState] with a new immutable object
     * containing the changes.
     */
    public fun updateTraceState(action: TraceStateMutator.() -> Unit)
}
