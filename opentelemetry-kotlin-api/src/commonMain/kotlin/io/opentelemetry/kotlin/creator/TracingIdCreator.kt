package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
public interface TracingIdCreator {

    /**
     * Generates a new ID for a span.
     */
    public fun generateSpanId(): String

    /**
     * Generates a new ID for a trace.
     */
    public fun generateTraceId(): String

    /**
     * An invalid trace ID.
     */
    public val invalidTraceId: String

    /**
     * An invalid span ID.
     */
    public val invalidSpanId: String
}
