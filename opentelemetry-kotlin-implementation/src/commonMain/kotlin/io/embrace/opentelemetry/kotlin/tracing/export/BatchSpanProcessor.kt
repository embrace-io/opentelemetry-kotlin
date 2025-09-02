package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.error.SdkErrorHandler
import io.embrace.opentelemetry.kotlin.error.SdkErrorSeverity
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.threadSafeList
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

/**
 * Configuration for [BatchSpanProcessor].
 */
@OptIn(ExperimentalApi::class)
public data class BatchSpanProcessorConfig(
    /**
     * The maximum number of spans to buffer before forcing an export.
     */
    val maxBatchSize: Int = 512,
    
    /**
     * The maximum size of the span queue. When the queue is full, new spans will be dropped.
     */
    val maxQueueSize: Int = 2048,
    
    /**
     * The timeout in milliseconds for the export operation.
     */
    val exportTimeout: Long = 30_000
)

/**
 * A [SpanProcessor] that batches spans and exports them in groups.
 * This processor collects spans until either the batch size is reached or the processor is flushed.
 * This implementation provides better throughput than [SimpleSpanProcessor] but with higher latency.
 * 
 * Note: This is a simplified implementation that exports when the batch size is reached or on flush/shutdown.
 * A full implementation would include timer-based exports for better latency characteristics.
 *
 * @param spanExporter The [SpanExporter] to export spans to
 * @param sdkErrorHandler Handler for SDK errors  
 * @param config Configuration for the batch processor
 */
@OptIn(ExperimentalApi::class)
public class BatchSpanProcessor(
    private val spanExporter: SpanExporter,
    private val sdkErrorHandler: SdkErrorHandler,
    private val config: BatchSpanProcessorConfig = BatchSpanProcessorConfig()
) : SpanProcessor {

    private val spanQueue = threadSafeList<SpanData>()
    private var isShutdown = false

    override fun onStart(span: ReadWriteSpan, parentContext: Context) {
        // No processing needed on start for batch processor
    }

    override fun onEnd(span: ReadableSpan) {
        if (isShutdown) {
            return
        }

        try {
            val spanData = span.toSpanData()
            
            // Drop span if queue is full
            if (spanQueue.size >= config.maxQueueSize) {
                sdkErrorHandler.onUserCodeError(
                    RuntimeException("Span queue is full, dropping span"),
                    "Queue overflow in BatchSpanProcessor", 
                    SdkErrorSeverity.WARNING
                )
                return
            }
            
            spanQueue.add(spanData)
            
            // Export batch if we've reached the max batch size
            if (spanQueue.size >= config.maxBatchSize) {
                exportBatch()
            }
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during span batching",
                SdkErrorSeverity.WARNING
            )
        }
    }

    override fun isStartRequired(): Boolean = false
    override fun isEndRequired(): Boolean = true

    override fun forceFlush(): OperationResultCode {
        if (isShutdown) {
            return OperationResultCode.Success
        }
        
        return try {
            exportBatch()
            spanExporter.forceFlush()
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during force flush",
                SdkErrorSeverity.WARNING
            )
            OperationResultCode.Failure
        }
    }

    override fun shutdown(): OperationResultCode {
        if (isShutdown) {
            return OperationResultCode.Success
        }
        
        isShutdown = true
        
        return try {
            // Export any remaining spans
            exportBatch()
            spanExporter.shutdown()
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during shutdown",
                SdkErrorSeverity.WARNING
            )
            OperationResultCode.Failure
        }
    }

    private fun exportBatch(): OperationResultCode {
        if (spanQueue.isEmpty()) {
            return OperationResultCode.Success
        }

        // Extract current batch
        val batch = mutableListOf<SpanData>()
        synchronized(spanQueue) {
            val batchSize = minOf(spanQueue.size, config.maxBatchSize)
            repeat(batchSize) {
                if (spanQueue.isNotEmpty()) {
                    batch.add(spanQueue.removeAt(0))
                }
            }
        }

        if (batch.isEmpty()) {
            return OperationResultCode.Success
        }

        return try {
            val result = spanExporter.export(batch)
            if (result != OperationResultCode.Success) {
                sdkErrorHandler.onUserCodeError(
                    RuntimeException("Batch export failed with result: $result"),
                    "Failed to export span batch",
                    SdkErrorSeverity.WARNING
                )
            }
            result
        } catch (throwable: Throwable) {
            sdkErrorHandler.onUserCodeError(
                throwable,
                "Exception during batch export",
                SdkErrorSeverity.WARNING
            )
            OperationResultCode.Failure
        }
    }
}