package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler

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
