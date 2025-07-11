package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Mutates [io.embrace.opentelemetry.kotlin.tracing.model.TraceState].
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracestate
 */
@ExperimentalApi
public interface TraceStateMutator {

    /**
     * Removes the key and its associated value from this TraceState.
     */
    public fun remove(key: String): String?

    /**
     * Adds a key-value pair to this TraceState.
     */
    public fun put(key: String, value: String)
}
