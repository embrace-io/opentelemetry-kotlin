package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
internal class InMemorySpanProcessor(private val exporter: InMemorySpanExporter) : SpanProcessor {

    override fun onStart(span: ReadWriteSpan, parentContext: Context) {
    }

    override fun onEnd(span: ReadableSpan) {
        exporter.export(listOf(span))
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
    override fun isStartRequired(): Boolean = true
    override fun isEndRequired(): Boolean = true
}
