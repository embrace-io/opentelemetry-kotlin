package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.NoopSpanContext
import io.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.kotlin.tracing.model.TraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal object NoopSpanContextCreator : SpanContextCreator {

    override val invalid: SpanContext = NoopSpanContext

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext = NoopSpanContext
}
