package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

/**
 * A factory for creating [io.embrace.opentelemetry.kotlin.tracing.model.SpanContext] instances.
 */
@ExperimentalApi
public interface SpanContextFactory {

    /**
     * Retrieves an invalid SpanContext.
     */
    public val invalid: SpanContext

    /**
     * Creates a new SpanContext.
     *
     * A valid traceId is a 32-character hex string (16 bytes) with at least one non-zero byte.
     * A valid spanId is a 16-character hex string (8 bytes) with at least one non-zero byte.
     *
     * If traceId or spanId are invalid (wrong format, length, or all zeros), they will be replaced with all zeros
     * and the returned SpanContext will have isValid = false.
     */
    public fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext
}
