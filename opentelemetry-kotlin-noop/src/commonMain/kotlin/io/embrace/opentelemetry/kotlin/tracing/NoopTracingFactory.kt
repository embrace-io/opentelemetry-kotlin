package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextOrigin
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

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
