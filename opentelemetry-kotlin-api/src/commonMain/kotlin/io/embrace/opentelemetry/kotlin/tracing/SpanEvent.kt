package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

/**
 * Represents an event that happened on a span
 */
@TracingDsl
@ExperimentalApi
public interface SpanEvent : AttributeContainer {

    /**
     * The name of the event
     */
    public val name: String

    /**
     * The timestamp of the event in nanoseconds
     */
    public val timestamp: Long
}
