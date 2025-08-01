package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * A read-only representation of a span event
 */
@ExperimentalApi
public interface EventData {
    /**
     * The name of the event
     */
    @ThreadSafe
    public val name: String

    /**
     * The timestamp of the event in nanoseconds
     */
    @ThreadSafe
    public val timestamp: Long

    /**
     * The attributes associated with the event.
     */
    @ThreadSafe
    public val attributes: Map<String, Any>
}
