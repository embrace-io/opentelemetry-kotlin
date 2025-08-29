package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.tracing.NoopSpan
import io.opentelemetry.kotlin.tracing.model.Span
import io.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal object NoopSpanCreator : SpanCreator {
    override val invalid: Span = NoopSpan
    override fun fromSpanContext(spanContext: SpanContext): Span = NoopSpan
    override fun fromContext(context: Context): Span = NoopSpan
}
