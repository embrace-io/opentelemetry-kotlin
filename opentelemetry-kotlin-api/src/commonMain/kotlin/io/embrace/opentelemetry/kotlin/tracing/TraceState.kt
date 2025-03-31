package io.embrace.opentelemetry.kotlin.tracing

/**
 * Holds key-pairs that describe vendor-specific information about a given trace.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracestate
 */
public interface TraceState {

    /**
     * Returns the value associated with the given key, or null if the key is not present.
     */
    public fun get(key: String): String?

    /**
     * Returns the trace state as a map of key-value pairs.
     */
    public fun asMap(): Map<String, String>
}
