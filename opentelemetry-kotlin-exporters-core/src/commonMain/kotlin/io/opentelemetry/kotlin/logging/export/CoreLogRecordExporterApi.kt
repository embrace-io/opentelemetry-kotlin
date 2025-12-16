@file:OptIn(ExperimentalApi::class)

package io.opentelemetry.kotlin.logging.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.opentelemetry.kotlin.export.EXPORT_TIMEOUT_MS
import io.opentelemetry.kotlin.export.MAX_EXPORT_BATCH_SIZE
import io.opentelemetry.kotlin.export.MAX_QUEUE_SIZE
import io.opentelemetry.kotlin.export.SCHEDULE_DELAY_MS

/**
 * Creates a composite log record processor that delegates to a list of processors.
 */
@ExperimentalApi
public fun createCompositeLogRecordProcessor(processors: List<LogRecordProcessor>): LogRecordProcessor {
    return _root_ide_package_.io.opentelemetry.kotlin.logging.export.CompositeLogRecordProcessor(
        processors,
        NoopSdkErrorHandler
    )
}

/**
 * Creates a simple log record processor that immediately sends any telemetry to its exporter.
 */
@ExperimentalApi
public fun createSimpleLogRecordProcessor(exporter: LogRecordExporter): LogRecordProcessor {
    return _root_ide_package_.io.opentelemetry.kotlin.logging.export.SimpleLogRecordProcessor(
        exporter
    )
}

/**
 * Creates a composite log record exporter that delegates to a list of exporters.
 */
@ExperimentalApi
public fun createCompositeLogRecordExporter(exporters: List<LogRecordExporter>): LogRecordExporter {
    return _root_ide_package_.io.opentelemetry.kotlin.logging.export.CompositeLogRecordExporter(
        exporters,
        NoopSdkErrorHandler
    )
}

/**
 * Creates a batching processor that sends telemetry in batches.
 * See https://opentelemetry.io/docs/specs/otel/logs/sdk/#batching-processor
 */
public fun createBatchLogRecordProcessor(
    exporter: LogRecordExporter,
    maxQueueSize: Int = _root_ide_package_.io.opentelemetry.kotlin.export.MAX_QUEUE_SIZE,
    scheduleDelayMs: Long = _root_ide_package_.io.opentelemetry.kotlin.export.SCHEDULE_DELAY_MS,
    exportTimeoutMs: Long = _root_ide_package_.io.opentelemetry.kotlin.export.EXPORT_TIMEOUT_MS,
    maxExportBatchSize: Int = _root_ide_package_.io.opentelemetry.kotlin.export.MAX_EXPORT_BATCH_SIZE
): LogRecordProcessor =
    _root_ide_package_.io.opentelemetry.kotlin.logging.export.BatchLogRecordProcessorImpl(
        exporter,
        maxQueueSize,
        scheduleDelayMs,
        exportTimeoutMs,
        maxExportBatchSize
    )
