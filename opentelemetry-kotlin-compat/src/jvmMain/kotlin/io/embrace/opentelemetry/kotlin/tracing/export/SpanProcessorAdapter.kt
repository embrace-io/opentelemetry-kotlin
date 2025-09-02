package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanProcessor
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.toOtelJavaContext
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.toOperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpanAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanAdapter

@OptIn(ExperimentalApi::class)
internal class SpanProcessorAdapter(
    private val impl: OtelJavaSpanProcessor
) : SpanProcessor {

    override fun onStart(
        span: ReadWriteSpan,
        parentContext: Context
    ) {
        if (span is ReadWriteSpanAdapter) {
            impl.onStart(parentContext.toOtelJavaContext(), span.impl)
        }
    }

    override fun onEnd(span: ReadableSpan) {
        if (span is ReadableSpanAdapter) {
            impl.onEnd(span.impl)
        }
    }

    override fun isStartRequired(): Boolean = impl.isStartRequired
    override fun isEndRequired(): Boolean = impl.isEndRequired
    override fun shutdown(): OperationResultCode = impl.shutdown().toOperationResultCode()
    override fun forceFlush(): OperationResultCode = impl.forceFlush().toOperationResultCode()
}
