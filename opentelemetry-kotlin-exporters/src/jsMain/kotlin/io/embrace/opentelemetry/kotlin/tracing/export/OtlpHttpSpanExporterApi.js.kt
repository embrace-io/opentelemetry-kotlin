package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
public actual fun createOtlpHttpSpanExporter(baseUrl: String): SpanExporter {
    throw UnsupportedOperationException()
}
