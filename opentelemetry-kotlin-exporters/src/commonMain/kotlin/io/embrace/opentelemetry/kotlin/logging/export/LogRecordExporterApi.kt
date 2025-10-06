@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler

/**
 * Creates a log record exporter that sends telemetry to the specified URL over OTLP.
 */
@ExperimentalApi
public expect fun createOtlpHttpLogRecordExporter(baseUrl: String): LogRecordExporter

/**
 * Creates a composite log record processor that delegates to a list of processors.
 */
@ExperimentalApi
public fun createCompositeLogRecordProcessor(processors: List<LogRecordProcessor>): LogRecordProcessor {
    return CompositeLogRecordProcessor(processors, NoopSdkErrorHandler)
}

/**
 * Creates a simple log record processor that immediately sends any telemetry to its exporter.
 */
@ExperimentalApi
public fun createSimpleLogRecordProcessor(exporter: LogRecordExporter): LogRecordProcessor {
    return SimpleLogRecordProcessor(exporter)
}

/**
 * Creates a composite log record exporter that delegates to a list of exporters.
 */
@ExperimentalApi
public fun createCompositeLogRecordExporter(exporters: List<LogRecordExporter>): LogRecordExporter {
    return CompositeLogRecordExporter(exporters, NoopSdkErrorHandler)
}
