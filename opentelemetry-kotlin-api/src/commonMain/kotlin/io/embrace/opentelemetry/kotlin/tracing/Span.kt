package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * A span represents a single operation within a trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#span
 */
@TracingDsl
@ExperimentalApi
@ThreadSafe
public interface Span : SpanRelationshipContainer {

    /**
     * Sets the name of the span. Must be non-empty.
     */
    @ThreadSafe
    public var name: String

    /**
     * Sets the status of the span. This defaults to [StatusCode.Unset].
     */
    @ThreadSafe
    public var status: StatusCode

    /**
     * Gets the parent span context. This defaults to null.
     */
    @ThreadSafe
    public val parent: SpanContext?

    /**
     * Gets the span context that uniquely identifies this span.
     */
    @ThreadSafe
    public val spanContext: SpanContext

    /**
     * Ends the span.
     */
    @ThreadSafe
    public fun end()

    /**
     * Ends the span, setting an explicit end-time in nanoseconds.
     */
    @ThreadSafe
    public fun end(timestamp: Long)

    /**
     * Returns true if the span is currently recording.
     */
    @ThreadSafe
    public fun isRecording(): Boolean
}
