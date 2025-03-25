package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

/**
 * Provides operations that add relationships (events + links) to a span
 */
public interface SpanRelationshipContainer : AttributeContainer {

    /**
     * Adds a link to the span that associates it with another [SpanContext].
     */
    public fun addLink(spanContext: SpanContext, action: Link.() -> Unit)

    /**
     * Adds an event to the span.
     */
    public fun addEvent(
        name: String,
        timestamp: Long? = null,
        action: SpanEvent.() -> Unit
    )

    /**
     * Returns a snapshot of the events on this span.
     */
    public fun events(): List<SpanEvent>

    /**
     * Returns a snapshot of the links on this span.
     */
    public fun links(): List<Link>
}
