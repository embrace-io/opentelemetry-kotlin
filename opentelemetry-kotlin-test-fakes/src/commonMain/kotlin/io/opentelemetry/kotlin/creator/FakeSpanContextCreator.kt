package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.FakeSpanContext
import io.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.kotlin.tracing.model.TraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class FakeSpanContextCreator : SpanContextCreator {

    override val invalid: SpanContext = FakeSpanContext(isValid = false)

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext = FakeSpanContext(traceId, spanId, traceFlags, traceState)
}
