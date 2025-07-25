package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Represents the state of a Span that must be serialized & propagated along a distributed context.
 * This consists of a trace ID (a 16-byte array with at least one non-zero byte) and a span ID
 * (an 8-byte array with at least one non-zero byte).
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#spancontext
 */
@ThreadSafe
@ExperimentalApi
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
    public val traceState: TraceState

    public companion object
}
