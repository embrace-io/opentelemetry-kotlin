package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.tracing.FakeSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class FakeSpanCreator : SpanCreator {
    override val invalid: Span = FakeSpan()
    override fun fromSpanContext(spanContext: SpanContext): Span = FakeSpan()
    override fun fromContext(context: Context): Span = FakeSpan()
}
