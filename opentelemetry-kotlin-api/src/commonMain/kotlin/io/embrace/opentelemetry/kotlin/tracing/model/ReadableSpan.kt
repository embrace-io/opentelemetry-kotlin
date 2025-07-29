package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

/**
 * A read-only representation of a span.
 */
@ExperimentalApi
public interface ReadableSpan : SpanData {

    /**
     * The span name
     */
    public override val name: String

    /**
     * The span status
     */
    public override val status: StatusData

    /**
     * The parent span context.
     */
    public override val parent: SpanContext

    /**
     * The span context that uniquely identifies this span.
     */
    public override val spanContext: SpanContext

    /**
     * The kind of this span
     */
    public override val spanKind: SpanKind

    /**
     * The timestamp at which this span started, in nanoseconds.
     */
    public override val startTimestamp: Long

    /**
     * The timestamp at which this span ended, in nanoseconds. If it has not ended null will return.
     */
    public override val endTimestamp: Long?

    /**
     * The resource associated with the object
     */
    public override val resource: Resource

    /**
     * The instrumentation scope information associated with the object
     */
    public override val instrumentationScopeInfo: InstrumentationScopeInfo

    /**
     * A map of attributes associated with the span.
     */
    public override val attributes: Map<String, Any>

    /**
     * A list of events associated with the span.
     */
    public override val events: List<EventData>

    /**
     * A list of links associated with the span.
     */
    public override val links: List<LinkData>

    /**
     * Return an immutable instance of the span at the time of invocation.
     */
    public fun toSpanData(): SpanData

    /**
     * Returns true if this span has ended.
     */
    public fun hasEnded(): Boolean
}
