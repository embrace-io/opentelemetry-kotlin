package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

/**
 * Provides operations that add relationships (events + links) to a span
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/
 */
@ExperimentalApi
public interface SpanRelationships : AttributeContainer {

    /**
     * Adds a link to the span that associates it with another [SpanContext].
     */
    @ThreadSafe
    public fun addLink(spanContext: SpanContext, attributes: AttributeContainer.() -> Unit = {})

    /**
     * Adds an event to the span.
     */
    @ThreadSafe
    public fun addEvent(
        name: String,
        timestamp: Long? = null,
        attributes: AttributeContainer.() -> Unit = {},
    )
}
