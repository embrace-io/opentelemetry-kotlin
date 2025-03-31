package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Record an exception on the span as an event.
 */
@Suppress("UNUSED_PARAMETER")
@ExperimentalApi
public fun Span.recordException(exception: Throwable, action: SpanEvent.() -> Unit) {
    TODO()
}
