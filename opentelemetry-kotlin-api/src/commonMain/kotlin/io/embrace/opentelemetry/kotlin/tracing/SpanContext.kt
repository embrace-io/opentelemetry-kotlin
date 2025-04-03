package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Represents the state of a Span that must be serialized & propagated along a distributed context.
 * This consists of a trace ID (a 16-byte array with at least one non-zero byte) and a span ID
 * (an 8-byte array with at least one non-zero byte).
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spancontext
 */
@ThreadSafe
public interface SpanContext {

    /**
     * Hexadecimal representation of trace ID.
     */
    @ThreadSafe
    public val traceId: String

    /**
     * Hexadecimal representation of span ID.
     */
    @ThreadSafe
    public val spanId: String

    /**
     * Contains details about the trace.
     */
    @ThreadSafe
    public val traceFlags: TraceFlags

    /**
     * True if the SpanContext has a valid trace ID and span ID.
     */
    @ThreadSafe
    public val isValid: Boolean

    /**
     * True if the SpanContext was propagated from a remote parent.
     */
    @ThreadSafe
    public val isRemote: Boolean

    /**
     * Contains state about the trace.
     */
    @ThreadSafe
    public fun getTraceState(): TraceState

    /**
     * Mutates the trace state with the given action and replaces [traceState] with a new immutable object
     * containing the changes.
     */
    @ThreadSafe
    public fun updateTraceState(action: TraceStateMutator.() -> Unit)

    /**
     * Creates a new [SpanContext] from the given parameters.
     */
    @ThreadSafe
    public fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
        origin: SpanContextOrigin = SpanContextOrigin.LOCAL,
    ): SpanContext
}
