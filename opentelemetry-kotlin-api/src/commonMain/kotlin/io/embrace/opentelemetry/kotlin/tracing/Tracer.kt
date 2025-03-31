package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * A Tracer is responsible for creating spans.
 */
@ExperimentalApi
public interface Tracer {

    /**
     * Creates a new span.
     */
    public fun createSpan(
        name: String,
        parent: Span? = null,
        spanKind: SpanKind = SpanKind.INTERNAL,
        startTimestamp: Long? = null,
        action: SpanRelationshipContainer.() -> Unit = {}
    ): Span
}
