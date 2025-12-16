package io.opentelemetry.kotlin.logging.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.logging.export.LogRecordExporter

@OptIn(ExperimentalApi::class)
public actual fun createOtlpHttpLogRecordExporter(baseUrl: String): LogRecordExporter {
    throw UnsupportedOperationException()
}
