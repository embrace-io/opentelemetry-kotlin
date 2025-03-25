package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.StatusCode

/**
 * A span represents a single operation within a trace.
 */
@TracingDsl
public interface Span : SpanRelationshipContainer {

    /**
     * Sets the name of the span. Must be non-empty.
     */
    public var name: String

    /**
     * Sets the status of the span. This defaults to [StatusCode.Unset].
     */
    public var status: StatusCode

    /**
     * Sets the kind of the span. This defaults to [SpanKind.INTERNAL].
     */
    public var spanKind: SpanKind

    /**
     * Sets the parent span context. This defaults to null.
     */
    public var parent: SpanContext?

    /**
     * Sets the start timestamp of the span. In span creation this defaults to the current time.
     */
    public fun updateStartTimestamp(timestamp: Long)

    /**
     * Ends the span. An optional timestamp in nanoseconds can be supplied.
     */
    public fun end(timestamp: Long? = null)

    /**
     * Returns true if the span is currently recording.
     */
    public val isRecording: Boolean
}
