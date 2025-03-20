package io.embrace.opentelemetry.kotlin.tracing

/**
 * A Tracer is responsible for creating spans.
 */
public interface Tracer {

    /**
     * Creates a new span.
     */
    public fun createSpan(
        name: String,
        action: Span.() -> Unit
    ): Span
}
