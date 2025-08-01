package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanProcessor
import io.embrace.opentelemetry.kotlin.j2k.bridge.context.toOtelKotlinContext
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.opentelemetry.context.Context

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanProcessorAdapter(
    private val impl: SpanProcessor
) : OtelJavaSpanProcessor {

    override fun onStart(parentContext: Context, span: OtelJavaReadWriteSpan) {
        impl.onStart(ReadWriteSpanAdapter(span), parentContext.toOtelKotlinContext())
    }

    override fun onEnd(span: OtelJavaReadableSpan) {
        impl.onEnd(ReadableSpanAdapter(span))
    }

    override fun isStartRequired(): Boolean = impl.isStartRequired()
    override fun isEndRequired(): Boolean = impl.isEndRequired()
}
