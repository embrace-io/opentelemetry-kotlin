package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * Holds key-pairs that describe vendor-specific information about a given trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracestate
 */
@ThreadSafe
@ExperimentalApi
public interface TraceState {

    /**
     * Returns the value associated with the given key, or null if the key is not present.
     */
    @ThreadSafe
    public fun get(key: String): String?

    /**
     * Returns the trace state as a map of key-value pairs.
     */
    @ThreadSafe
    public fun asMap(): Map<String, String>
}
