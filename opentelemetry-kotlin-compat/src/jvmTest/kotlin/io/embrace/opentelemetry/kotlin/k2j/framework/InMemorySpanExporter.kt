package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.export.SpanExporter
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

@OptIn(ExperimentalApi::class)
internal class InMemorySpanExporter : SpanExporter {

    private val impl = mutableListOf<ReadableSpan>()

    val exportedSpans: List<ReadableSpan>
        get() = impl

    override fun export(telemetry: List<ReadableSpan>): OperationResultCode {
        impl += telemetry
        return OperationResultCode.Success
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
