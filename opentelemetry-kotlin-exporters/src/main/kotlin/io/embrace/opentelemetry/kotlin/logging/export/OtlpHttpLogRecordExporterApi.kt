package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OtlpClient
import io.embrace.opentelemetry.kotlin.export.exportInitialDelayMs
import io.embrace.opentelemetry.kotlin.export.exportMaxAttemptIntervalMs
import io.embrace.opentelemetry.kotlin.export.exportMaxAttempts

/**
 * Creates a log record exporter that sends telemetry to the specified URL over OTLP.
 */
@OptIn(ExperimentalApi::class)
fun createOtlpHttpLogRecordExporter(baseUrl: String): LogRecordExporter = OtlpHttpLogRecordExporter(
    OtlpClient(baseUrl),
    exportInitialDelayMs,
    exportMaxAttemptIntervalMs,
    exportMaxAttempts
)
