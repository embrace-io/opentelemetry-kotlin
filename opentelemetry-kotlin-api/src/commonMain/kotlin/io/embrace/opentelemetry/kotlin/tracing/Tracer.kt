package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.context.Context

/**
 * A Tracer is responsible for creating spans.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#tracerprovider
 */
@ExperimentalApi
@ThreadSafe
public interface Tracer {

    /**
     * Creates a new span.
     */
    @ThreadSafe
    public fun createSpan(
        name: String,
        parent: SpanContext? = null,
        spanKind: SpanKind = SpanKind.INTERNAL,
        startTimestamp: Long? = null,
        context: Context? = null,
        action: SpanRelationships.() -> Unit = {}
    ): Span
}
