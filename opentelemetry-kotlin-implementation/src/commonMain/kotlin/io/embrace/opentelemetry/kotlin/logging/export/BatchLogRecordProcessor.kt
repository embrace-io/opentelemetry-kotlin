package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ReentrantReadWriteLock
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.logging.model.ReadWriteLogRecord
import io.embrace.opentelemetry.kotlin.threadSafeList

/**
 * A [LogRecordProcessor] that batches log records and exports them in groups.
 *
 * This processor collects log records into batches and exports them when:
 * - The batch reaches the maximum size
 * - [forceFlush] is called
 * - [shutdown] is called
 *
 * @param exporter The [LogRecordExporter] to export batched log records to
 * @param maxBatchSize The maximum number of log records to batch before exporting (default: 512)
 * @param maxQueueSize The maximum number of log records to queue before dropping (default: 2048)
 */
@OptIn(ExperimentalApi::class)
public class BatchLogRecordProcessor(
    private val exporter: LogRecordExporter,
    private val maxBatchSize: Int = DEFAULT_MAX_BATCH_SIZE,
    private val maxQueueSize: Int = DEFAULT_MAX_QUEUE_SIZE
) : LogRecordProcessor {

    private val buffer = threadSafeList<ReadWriteLogRecord>()
    private val lock = ReentrantReadWriteLock()
    private var isShutdown = false

    override fun onEmit(log: ReadWriteLogRecord, context: Context) {
        lock.read {
            if (isShutdown) {
                return
            }
        }

        lock.write {
            if (isShutdown) {
                return
            }

            // Drop log records if queue is full
            if (buffer.size >= maxQueueSize) {
                return
            }

            buffer.add(log)

            // Export if batch is full
            if (buffer.size >= maxBatchSize) {
                exportBatch()
            }
        }
    }

    override fun forceFlush(): OperationResultCode {
        return lock.write {
            if (isShutdown) {
                OperationResultCode.Success
            } else {
                val result = exportBatch()
                if (result == OperationResultCode.Success) {
                    exporter.forceFlush()
                } else {
                    result
                }
            }
        }
    }

    override fun shutdown(): OperationResultCode {
        return lock.write {
            if (isShutdown) {
                OperationResultCode.Success
            } else {
                isShutdown = true
                val flushResult = exportBatch()
                val shutdownResult = exporter.shutdown()
                
                // Return failure if either operation failed
                if (flushResult == OperationResultCode.Failure || shutdownResult == OperationResultCode.Failure) {
                    OperationResultCode.Failure
                } else {
                    OperationResultCode.Success
                }
            }
        }
    }

    /**
     * Exports the current batch of log records. Must be called within a write lock.
     *
     * @return The result of the export operation
     */
    private fun exportBatch(): OperationResultCode {
        if (buffer.isEmpty()) {
            return OperationResultCode.Success
        }

        val batch = buffer.toList()
        buffer.clear()

        return exporter.export(batch)
    }

    public companion object {
        /**
         * The default maximum batch size.
         */
        public const val DEFAULT_MAX_BATCH_SIZE: Int = 512

        /**
         * The default maximum queue size.
         */
        public const val DEFAULT_MAX_QUEUE_SIZE: Int = 2048
    }
}