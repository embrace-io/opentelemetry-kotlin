package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ThreadSafe

public interface TracingFactory {

    /**
     * Creates a new [SpanContext] from the given parameters.
     */
    @ThreadSafe
    public fun createSpanContext(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
        origin: SpanContextOrigin = SpanContextOrigin.LOCAL,
    ): SpanContext
}
