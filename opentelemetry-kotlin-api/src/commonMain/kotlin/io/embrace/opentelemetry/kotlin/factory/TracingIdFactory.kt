package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
public interface TracingIdFactory {

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
