package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

/**
 * Immutable representation of a Span used for exporting
 */
@ExperimentalApi
public interface SpanData {
    /**
     * The span name
     */
    public val name: String

    /**
     * The span status
     */
    public val status: StatusData

    /**
     * The parent span context.
     */
    public val parent: SpanContext

    /**
     * The span context that uniquely identifies this span.
     */
    public val spanContext: SpanContext

    /**
     * The kind of this span
     */
    public val spanKind: SpanKind

    /**
     * The timestamp at which this span started, in nanoseconds.
     */
    public val startTimestamp: Long

    /**
     * The timestamp at which this span ended, in nanoseconds. If it has not ended null will return.
     */
    public val endTimestamp: Long?

    /**
     * A map of attributes associated with the span.
     */
    public val attributes: Map<String, Any>

    /**
     * A list of events associated with the span.
     */
    public val events: List<EventData>

    /**
     * A list of links associated with the span.
     */
    public val links: List<LinkData>

    /**
     * The resource associated with the object
     */
    public val resource: Resource

    /**
     * The instrumentation scope information associated with the object
     */
    public val instrumentationScopeInfo: InstrumentationScopeInfo
}
