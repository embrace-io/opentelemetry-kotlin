package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.CompositeTelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.batchExportOperation
import io.embrace.opentelemetry.kotlin.logging.model.ReadableLogRecord

@OptIn(ExperimentalApi::class)
internal class CompositeLogRecordExporter(
    private val exporters: List<LogRecordExporter>,
    private val sdkErrorHandler: SdkErrorHandler,
    private val telemetryCloseable: CompositeTelemetryCloseable = CompositeTelemetryCloseable(
        exporters,
        sdkErrorHandler,
    )
) : LogRecordExporter, TelemetryCloseable by telemetryCloseable {

    override fun export(telemetry: List<ReadableLogRecord>): OperationResultCode {
        return batchExportOperation(exporters, sdkErrorHandler) {
            it.export(telemetry)
        }
    }
}
