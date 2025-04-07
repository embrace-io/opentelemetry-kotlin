package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
internal object NoopTracingFactory : TracingFactory {

    override fun createSpanContext(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
        origin: SpanContextOrigin
    ): SpanContext = NoopSpanContext
}
