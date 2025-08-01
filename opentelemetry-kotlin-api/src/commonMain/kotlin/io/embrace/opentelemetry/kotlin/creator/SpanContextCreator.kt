package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

/**
 * A factory for creating [SpanContext] instances.
 */
@ExperimentalApi
public interface SpanContextCreator {

    /**
     * Retrieves an invalid SpanContext.
     */
    public val invalid: SpanContext

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
