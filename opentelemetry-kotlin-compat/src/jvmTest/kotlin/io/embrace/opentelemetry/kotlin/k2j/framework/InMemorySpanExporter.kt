package io.embrace.opentelemetry.kotlin.k2j.framework

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.export.SpanExporter

@OptIn(ExperimentalApi::class)
internal class InMemorySpanExporter : SpanExporter {

    private val impl = mutableListOf<SpanData>()

    val exportedSpans: List<SpanData>
        get() = impl

    override fun export(telemetry: List<SpanData>): OperationResultCode {
        impl += telemetry
        return OperationResultCode.Success
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
