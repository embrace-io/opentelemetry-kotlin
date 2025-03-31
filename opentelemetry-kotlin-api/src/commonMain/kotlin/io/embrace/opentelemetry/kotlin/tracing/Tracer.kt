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
        startTimestamp: Long? = null,
        action: SpanRelationshipContainer.() -> Unit = {}
    ): Span
}
