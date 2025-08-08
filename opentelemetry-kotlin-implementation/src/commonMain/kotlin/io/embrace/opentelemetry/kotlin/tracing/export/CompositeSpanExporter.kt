package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.CompositeTelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.batchExportOperation
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
internal class CompositeSpanExporter(
    private val exporters: List<SpanExporter>,
    private val sdkErrorHandler: SdkErrorHandler,
    private val telemetryCloseable: CompositeTelemetryCloseable = CompositeTelemetryCloseable(
        exporters,
        sdkErrorHandler,
    )
) : SpanExporter, TelemetryCloseable by telemetryCloseable {

    override fun export(telemetry: List<SpanData>): OperationResultCode =
        batchExportOperation(exporters, sdkErrorHandler) {
            it.export(telemetry)
        }
}
