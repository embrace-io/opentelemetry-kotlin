package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

/**
 * A simple implementation of [LogRecordProcessor] that immediately exports each log record
 * as it is emitted, without any batching.
 *
 * This processor is ideal for debugging or low-throughput scenarios where immediate export
 * is desired. For production environments with high throughput, consider using
 * [BatchLogRecordProcessor] instead.
 */
@OptIn(ExperimentalApi::class)
public class SimpleLogRecordProcessor(
    private val exporter: LogRecordExporter
) : LogRecordProcessor {

    override fun onEmit(log: ReadWriteLogRecord, context: Context) {
        exporter.export(listOf(log))
    }

    override fun forceFlush(): OperationResultCode = exporter.forceFlush()

    override fun shutdown(): OperationResultCode = exporter.shutdown()
}