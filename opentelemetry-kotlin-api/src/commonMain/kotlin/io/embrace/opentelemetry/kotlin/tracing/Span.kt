package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.StatusCode

/**
 * A span represents a single operation within a trace.
 */
@TracingDsl
@ExperimentalApi
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
     * Gets the parent span context. This defaults to null.
     */
    public val parent: SpanContext?

    /**
     * Gets the span context that uniquely identifies this span.
     */
    public val spanContext: SpanContext

    /**
     * Ends the span. An optional timestamp in nanoseconds can be supplied.
     */
    public fun end(timestamp: Long? = null)

    /**
     * Returns true if the span is currently recording.
     */
    public fun isRecording(): Boolean
}
