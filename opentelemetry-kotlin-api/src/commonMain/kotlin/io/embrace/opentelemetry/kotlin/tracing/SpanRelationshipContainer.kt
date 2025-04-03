package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

/**
 * Provides operations that add relationships (events + links) to a span
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/
 */
@ExperimentalApi
public interface SpanRelationshipContainer : AttributeContainer {

    /**
     * Adds a link to the span that associates it with another [SpanContext].
     */
    @ThreadSafe
    public fun addLink(spanContext: SpanContext, action: AttributeContainer.() -> Unit)

    /**
     * Adds an event to the span.
     */
    @ThreadSafe
    public fun addEvent(
        name: String,
        timestamp: Long? = null,
        action: AttributeContainer.() -> Unit
    )

    /**
     * Returns a snapshot of the events on this span.
     */
    @ThreadSafe
    public fun events(): List<SpanEvent>

    /**
     * Returns a snapshot of the links on this span.
     */
    @ThreadSafe
    public fun links(): List<Link>
}
