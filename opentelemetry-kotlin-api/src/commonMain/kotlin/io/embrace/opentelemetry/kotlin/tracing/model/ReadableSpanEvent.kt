package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * A read-only representation of a span event.
 */
@ExperimentalApi
public interface ReadableSpanEvent {

    /**
     * The name of the event
     */
    public val name: String

    /**
     * The timestamp of the event in nanoseconds
     */
    public val timestamp: Long

    /**
     * The attributes associated with the event.
     */
    public val attributes: Map<String, Any>
}
