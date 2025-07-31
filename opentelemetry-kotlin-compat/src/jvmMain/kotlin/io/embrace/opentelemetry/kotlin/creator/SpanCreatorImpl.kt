package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.k2j.tracing.fromContext
import io.embrace.opentelemetry.kotlin.k2j.tracing.fromSpanContext
import io.embrace.opentelemetry.kotlin.k2j.tracing.invalid
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class SpanCreatorImpl : SpanCreator {

    override val invalid: Span = Span.invalid()

    override fun fromSpanContext(spanContext: SpanContext): Span = Span.fromSpanContext(spanContext)

    override fun fromContext(context: Context): Span = Span.fromContext(context)
}
