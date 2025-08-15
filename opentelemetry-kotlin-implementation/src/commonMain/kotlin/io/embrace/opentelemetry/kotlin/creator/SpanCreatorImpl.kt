package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.tracing.NonRecordingSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

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
