package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class SpanCreatorImpl(private val spanKey: ContextKey<Span>) : SpanCreator {

    override val invalid: Span
        get() = throw UnsupportedOperationException()

    override fun fromSpanContext(spanContext: SpanContext): Span {
        throw UnsupportedOperationException()
    }

    override fun fromContext(context: Context): Span {
        return context.get(spanKey) ?: invalid
    }
}
