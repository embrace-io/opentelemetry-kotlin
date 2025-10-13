package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ReentrantReadWriteLock
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.CompositeTelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.export.batchExportOperation
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

@OptIn(ExperimentalApi::class)
internal class CompositeLogRecordProcessor(
    private val processors: List<LogRecordProcessor>,
    private val sdkErrorHandler: SdkErrorHandler,
    private val telemetryCloseable: TelemetryCloseable = CompositeTelemetryCloseable(
        processors,
        sdkErrorHandler
    ),
) : LogRecordProcessor, TelemetryCloseable by telemetryCloseable {

    private val lock = ReentrantReadWriteLock()

    override fun onEmit(log: ReadWriteLogRecord, context: Context) {
        lock.write {
            batchExportOperation(processors, sdkErrorHandler) {
                it.onEmit(log, context)
                OperationResultCode.Success
            }
        }
    }
}
