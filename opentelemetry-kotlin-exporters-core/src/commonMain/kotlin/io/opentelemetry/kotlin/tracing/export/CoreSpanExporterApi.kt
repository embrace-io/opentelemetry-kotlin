package io.opentelemetry.kotlin.tracing.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.opentelemetry.kotlin.export.EXPORT_TIMEOUT_MS
import io.opentelemetry.kotlin.export.MAX_EXPORT_BATCH_SIZE
import io.opentelemetry.kotlin.export.MAX_QUEUE_SIZE
import io.opentelemetry.kotlin.export.SCHEDULE_DELAY_MS

/**
 * Creates a composite span processor that delegates to a list of processors.
 */
@ExperimentalApi
public fun createCompositeSpanProcessor(processors: List<SpanProcessor>): SpanProcessor {
    return _root_ide_package_.io.opentelemetry.kotlin.tracing.export.CompositeSpanProcessor(
        processors,
        NoopSdkErrorHandler
    )
}

/**
 * Creates a simple span processor that immediately sends any telemetry to its exporter.
 */
@ExperimentalApi
public fun createSimpleSpanProcessor(exporter: SpanExporter): SpanProcessor {
    return _root_ide_package_.io.opentelemetry.kotlin.tracing.export.SimpleSpanProcessor(exporter)
}

/**
 * Creates a composite span exporter that delegates to a list of exporters.
 */
@ExperimentalApi
public fun createCompositeSpanExporter(exporters: List<SpanExporter>): SpanExporter {
    return _root_ide_package_.io.opentelemetry.kotlin.tracing.export.CompositeSpanExporter(
        exporters,
        NoopSdkErrorHandler
    )
}

/**
 * Creates a batching processor that sends telemetry in batches.
 * See https://opentelemetry.io/docs/specs/otel/logs/sdk/#batching-processor
 */
@OptIn(ExperimentalApi::class)
public fun createBatchSpanProcessor(
    exporter: SpanExporter,
    maxQueueSize: Int = _root_ide_package_.io.opentelemetry.kotlin.export.MAX_QUEUE_SIZE,
    scheduleDelayMs: Long = _root_ide_package_.io.opentelemetry.kotlin.export.SCHEDULE_DELAY_MS,
    exportTimeoutMs: Long = _root_ide_package_.io.opentelemetry.kotlin.export.EXPORT_TIMEOUT_MS,
    maxExportBatchSize: Int = _root_ide_package_.io.opentelemetry.kotlin.export.MAX_EXPORT_BATCH_SIZE
): SpanProcessor = _root_ide_package_.io.opentelemetry.kotlin.tracing.export.BatchSpanProcessorImpl(
    exporter,
    maxQueueSize,
    scheduleDelayMs,
    exportTimeoutMs,
    maxExportBatchSize
)
