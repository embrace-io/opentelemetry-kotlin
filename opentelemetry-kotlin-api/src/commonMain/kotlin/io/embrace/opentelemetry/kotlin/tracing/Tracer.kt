package io.embrace.opentelemetry.kotlin.tracing

/**
 * A Tracer is responsible for creating spans.
 */
public interface Tracer {

    /**
     * Returns true if the Tracer is enabled. This value may change over time.
     */
    public val enabled: Boolean

    /**
     * Creates a new span.
     */
    public fun createSpan(
        name: String,
        action: Span.() -> Unit
    ): Span
}
