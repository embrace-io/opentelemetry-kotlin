package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Immutable representation of a span event
 */
@ExperimentalApi
public interface EventData {
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
