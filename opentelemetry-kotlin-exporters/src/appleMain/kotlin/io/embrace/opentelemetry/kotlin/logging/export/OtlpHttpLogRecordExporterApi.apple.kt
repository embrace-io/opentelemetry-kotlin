package io.embrace.opentelemetry.kotlin.logging.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
public actual fun createOtlpHttpLogRecordExporter(baseUrl: String): LogRecordExporter {
    throw UnsupportedOperationException()
}
