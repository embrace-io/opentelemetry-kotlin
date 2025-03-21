package io.embrace.opentelemetry.kotlin.tracing

/**
 * Record an exception on the span as an event.
 */
@Suppress("UNUSED_PARAMETER")
public fun Span.recordException(exception: Throwable, action: SpanEvent.() -> Unit) {
    TODO()
}
