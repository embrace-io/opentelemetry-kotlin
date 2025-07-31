package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.tracing.NoopSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal object NoopSpanCreator : SpanCreator {
    override val invalid: Span = NoopSpan
    override fun fromSpanContext(spanContext: SpanContext): Span = NoopSpan
    override fun fromContext(context: Context): Span = NoopSpan
}
