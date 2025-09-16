package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.tracing.NonRecordingSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class SpanFactoryImpl(
    spanContextFactory: SpanContextFactory,
    private val spanKey: ContextKey<Span>
) : SpanFactory {

    private val invalidSpanContext by lazy { spanContextFactory.invalid }

    override val invalid: Span by lazy { NonRecordingSpan(invalidSpanContext, invalidSpanContext) }

    override fun fromSpanContext(spanContext: SpanContext): Span =
        NonRecordingSpan(invalidSpanContext, spanContext)

    override fun fromContext(context: Context): Span {
        return context.get(spanKey) ?: invalid
    }
}
