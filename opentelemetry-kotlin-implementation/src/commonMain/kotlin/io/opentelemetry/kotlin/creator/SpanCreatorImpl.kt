package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.ContextKey
import io.opentelemetry.kotlin.tracing.NonRecordingSpan
import io.opentelemetry.kotlin.tracing.model.Span
import io.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class SpanCreatorImpl(
    spanContextCreator: SpanContextCreator,
    private val spanKey: ContextKey<Span>
) : SpanCreator {

    private val invalidSpanContext = spanContextCreator.invalid

    override val invalid: Span = NonRecordingSpan(invalidSpanContext, invalidSpanContext)

    override fun fromSpanContext(spanContext: SpanContext): Span =
        NonRecordingSpan(invalidSpanContext, spanContext)

    override fun fromContext(context: Context): Span {
        return context.get(spanKey) ?: invalid
    }
}
