package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.export.EXPORT_TIMEOUT_MS
import io.embrace.opentelemetry.kotlin.export.MAX_EXPORT_BATCH_SIZE
import io.embrace.opentelemetry.kotlin.export.MAX_QUEUE_SIZE
import io.embrace.opentelemetry.kotlin.export.SCHEDULE_DELAY_MS

/**
 * Creates a span exporter that sends telemetry to the specified URL over OTLP.
 */
@ExperimentalApi
public expect fun createOtlpHttpSpanExporter(baseUrl: String): SpanExporter

/**
 * Creates a composite span processor that delegates to a list of processors.
 */
@ExperimentalApi
public fun createCompositeSpanProcessor(processors: List<SpanProcessor>): SpanProcessor {
    return CompositeSpanProcessor(processors, NoopSdkErrorHandler)
}

/**
 * Creates a simple span processor that immediately sends any telemetry to its exporter.
 */
@ExperimentalApi
public fun createSimpleSpanProcessor(exporter: SpanExporter): SpanProcessor {
    return SimpleSpanProcessor(exporter)
}

/**
 * Creates a composite span exporter that delegates to a list of exporters.
 */
@ExperimentalApi
public fun createCompositeSpanExporter(exporters: List<SpanExporter>): SpanExporter {
    return CompositeSpanExporter(exporters, NoopSdkErrorHandler)
}

/**
 * Creates a batching processor that sends telemetry in batches.
 * See https://opentelemetry.io/docs/specs/otel/logs/sdk/#batching-processor
 */
@OptIn(ExperimentalApi::class)
public fun createBatchSpanProcessor(
    exporter: SpanExporter,
    maxQueueSize: Int = MAX_QUEUE_SIZE,
    scheduleDelayMs: Long = SCHEDULE_DELAY_MS,
    exportTimeoutMs: Long = EXPORT_TIMEOUT_MS,
    maxExportBatchSize: Int = MAX_EXPORT_BATCH_SIZE
): SpanProcessor = BatchSpanProcessorImpl(
    exporter,
    maxQueueSize,
    scheduleDelayMs,
    exportTimeoutMs,
    maxExportBatchSize
)
