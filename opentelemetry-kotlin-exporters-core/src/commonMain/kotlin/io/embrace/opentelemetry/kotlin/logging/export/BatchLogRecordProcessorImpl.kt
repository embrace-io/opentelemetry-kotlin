package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.BatchTelemetryProcessor
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

@OptIn(ExperimentalApi::class)
internal class BatchLogRecordProcessorImpl(
    private val exporter: LogRecordExporter,
    private val maxQueueSize: Int,
    private val scheduleDelayMs: Long,
    private val exportTimeoutMs: Long,
    private val maxExportBatchSize: Int,
) : LogRecordProcessor {

    private val processor = BatchTelemetryProcessor(
        maxQueueSize = maxQueueSize,
        scheduleDelayMs = scheduleDelayMs,
        exportTimeoutMs = exportTimeoutMs,
        maxExportBatchSize = maxExportBatchSize,
        exportAction = exporter::export
    )

    override fun onEmit(
        log: ReadWriteLogRecord,
        context: Context
    ) = processor.processTelemetry(log)

    override fun forceFlush(): OperationResultCode = processor.forceFlush()
    override fun shutdown(): OperationResultCode = processor.shutdown()
}
