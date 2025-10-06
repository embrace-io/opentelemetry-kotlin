package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OtlpClient
import io.embrace.opentelemetry.kotlin.export.exportInitialDelayMs
import io.embrace.opentelemetry.kotlin.export.exportMaxAttemptIntervalMs
import io.embrace.opentelemetry.kotlin.export.exportMaxAttempts

/**
 * Creates a span exporter that sends telemetry to the specified URL over OTLP.
 */
@OptIn(ExperimentalApi::class)
fun createOtlpHttpSpanExporter(baseUrl: String): SpanExporter = OtlpHttpSpanExporter(
    OtlpClient(baseUrl),
    exportInitialDelayMs,
    exportMaxAttemptIntervalMs,
    exportMaxAttempts
)
