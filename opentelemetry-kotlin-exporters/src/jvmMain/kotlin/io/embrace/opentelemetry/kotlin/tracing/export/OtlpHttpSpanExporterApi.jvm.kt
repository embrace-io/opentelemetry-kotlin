package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.EXPORT_INITIAL_DELAY_MS
import io.embrace.opentelemetry.kotlin.export.EXPORT_MAX_ATTEMPTS
import io.embrace.opentelemetry.kotlin.export.EXPORT_MAX_ATTEMPT_INTERVAL_MS
import io.embrace.opentelemetry.kotlin.export.OtlpClient

@OptIn(ExperimentalApi::class)
public actual fun createOtlpHttpSpanExporter(baseUrl: String): SpanExporter = OtlpHttpSpanExporter(
    OtlpClient(baseUrl),
    EXPORT_INITIAL_DELAY_MS,
    EXPORT_MAX_ATTEMPT_INTERVAL_MS,
    EXPORT_MAX_ATTEMPTS
)
