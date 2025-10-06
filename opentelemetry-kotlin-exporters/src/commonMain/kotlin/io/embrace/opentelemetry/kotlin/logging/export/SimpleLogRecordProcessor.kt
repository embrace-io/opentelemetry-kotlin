package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ReentrantReadWriteLock
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord

/**
 * A simple log record processor that immediately exports log records to a [LogRecordExporter].
 *
 * https://opentelemetry.io/docs/specs/otel/logs/sdk/#built-in-processors
 */
@OptIn(ExperimentalApi::class)
internal class SimpleLogRecordProcessor(
    private val exporter: LogRecordExporter,
) : LogRecordProcessor {

    private val lock = ReentrantReadWriteLock()

    override fun onEmit(
        log: ReadWriteLogRecord,
        context: Context
    ) {
        lock.write {
            exporter.export(listOf(log))
        }
    }

    override fun forceFlush(): OperationResultCode = OperationResultCode.Success
    override fun shutdown(): OperationResultCode = OperationResultCode.Success
}
