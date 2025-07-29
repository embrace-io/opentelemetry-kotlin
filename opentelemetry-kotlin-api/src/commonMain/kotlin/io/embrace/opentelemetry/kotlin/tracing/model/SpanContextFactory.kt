package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
public interface SpanContextFactory {

    /**
     * Creates an invalid SpanContext.
     */
    public fun invalid(): SpanContext

    /**
     * Creates a new SpanContext.
     */
    public fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext
}
