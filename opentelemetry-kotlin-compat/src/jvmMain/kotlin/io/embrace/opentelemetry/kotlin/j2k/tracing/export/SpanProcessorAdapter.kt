package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanProcessor
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.j2k.toOperationResultCode
import io.embrace.opentelemetry.kotlin.k2j.context.ContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyRepository
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
public class SpanProcessorAdapter(
    private val impl: OtelJavaSpanProcessor
) : SpanProcessor {

    override fun onStart(span: ReadWriteSpan, parentContext: Context) {
        impl.onStart(
            ContextAdapter(parentContext, ContextKeyRepository.INSTANCE),
            span.toOtelJavaReadWriteSpan()
        )
    }

    override fun onEnd(span: ReadableSpan) {
        impl.onEnd(span.toOtelJavaReadableSpan())
    }

    override fun shutdown(): OperationResultCode = impl.shutdown().toOperationResultCode()
    override fun forceFlush(): OperationResultCode = impl.forceFlush().toOperationResultCode()
}
