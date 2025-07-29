package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.TracingDsl
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

/**
 * A span represents a single operation within a trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#span
 */
@TracingDsl
@ExperimentalApi
@ThreadSafe
public interface Span : SpanRelationships {

    /**
     * Sets the name of the span. Must be non-empty.
     */
    @ThreadSafe
    public var name: String

    /**
     * Sets the status of the span. This defaults to [StatusCode.Unset].
     */
    @ThreadSafe
    public var status: StatusData

    /**
     * Gets the parent span context.
     */
    @ThreadSafe
    public val parent: SpanContext

    /**
     * Gets the span context that uniquely identifies this span.
     */
    @ThreadSafe
    public val spanContext: SpanContext

    /**
     * The kind of this span
     */
    @ThreadSafe
    public val spanKind: SpanKind

    /**
     * The timestamp at which this span started, in nanoseconds.
     */
    @ThreadSafe
    public val startTimestamp: Long

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
