@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.tracing.model.invalid
import io.embrace.opentelemetry.kotlin.tracing.NonRecordingSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

private val invalidSpan = NonRecordingSpan(SpanContext.invalid(), SpanContext.invalid())

/**
 * Returns an invalid span.
 */
public fun Span.Companion.invalid(): Span = invalidSpan

/**
 * Returns a span from the supplied [SpanContext], or the return value of [Span.Companion.invalid]
 * if the [SpanContext] is invalid.
 */
public fun Span.Companion.fromSpanContext(spanContext: SpanContext): Span = when {
    spanContext.isValid -> NonRecordingSpan(SpanContext.invalid(), spanContext)
    else -> invalid()
}
