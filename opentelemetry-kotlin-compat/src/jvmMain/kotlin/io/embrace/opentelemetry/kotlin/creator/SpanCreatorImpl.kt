package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.j2k.bridge.context.OtelJavaContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.NonRecordingSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.api.trace.otelJavaSpanContextKey

@OptIn(ExperimentalApi::class)
internal class SpanCreatorImpl(spanContextCreator: SpanContextCreator) : SpanCreator {

    private val invalidSpanContext by lazy { spanContextCreator.invalid }

    override val invalid: Span by lazy { NonRecordingSpan(invalidSpanContext, invalidSpanContext) }

    override fun fromSpanContext(spanContext: SpanContext): Span = when {
        spanContext.isValid -> NonRecordingSpan(invalidSpanContext, spanContext)
        else -> invalid
    }

    override fun fromContext(context: Context): Span {
        val otelJavaCtx = (context as? OtelJavaContextAdapter)?.impl ?: OtelJavaContext.root()
        val span: OtelJavaSpan =
            otelJavaCtx.get(otelJavaSpanContextKey) ?: OtelJavaSpan.getInvalid()
        return NonRecordingSpan(invalid.parent, SpanContextAdapter(span.spanContext))
    }
}
