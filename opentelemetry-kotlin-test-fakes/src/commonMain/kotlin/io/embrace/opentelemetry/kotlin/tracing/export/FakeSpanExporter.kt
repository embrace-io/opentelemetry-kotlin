package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
class FakeSpanExporter(
    val exportReturnValue: (List<SpanData>) -> OperationResultCode = { OperationResultCode.Success },
    val forceFlushReturnValue: () -> OperationResultCode = { OperationResultCode.Success },
    val shutdownReturnValue: () -> OperationResultCode = { OperationResultCode.Success },
) : SpanExporter {

    val exports = mutableListOf<SpanData>()

    override fun export(telemetry: List<SpanData>): OperationResultCode {
        exports += telemetry
        return exportReturnValue(telemetry)
    }

    override fun forceFlush(): OperationResultCode = forceFlushReturnValue()
    override fun shutdown(): OperationResultCode = shutdownReturnValue()
}
