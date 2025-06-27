package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextOrigin
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

public interface TracingFactory {

    /**
     * Creates a new [io.embrace.opentelemetry.kotlin.tracing.model.SpanContext] from the given parameters.
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
