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
        parent: SpanContext? = null,
        spanKind: SpanKind = SpanKind.INTERNAL,
        startTimestamp: Long = 0, // TODO: supply via clock
        action: SpanRelationshipContainer.() -> Unit
    ): Span
}
