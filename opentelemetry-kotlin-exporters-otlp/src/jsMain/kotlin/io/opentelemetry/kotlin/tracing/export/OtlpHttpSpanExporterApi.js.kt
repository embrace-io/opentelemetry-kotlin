package io.opentelemetry.kotlin.tracing.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.export.SpanExporter

@OptIn(ExperimentalApi::class)
public actual fun createOtlpHttpSpanExporter(baseUrl: String): SpanExporter {
    throw UnsupportedOperationException()
}
