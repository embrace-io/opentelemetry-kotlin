package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent

/**
 * Record an exception on the span as an event.
 */
@Suppress("UNUSED_PARAMETER")
@ExperimentalApi
@ThreadSafe
public fun Span.recordException(exception: Throwable, action: SpanEvent.() -> Unit) {
    TODO()
}
