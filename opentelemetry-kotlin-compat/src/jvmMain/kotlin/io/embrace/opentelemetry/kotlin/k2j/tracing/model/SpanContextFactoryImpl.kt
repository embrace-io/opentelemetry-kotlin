package io.embrace.opentelemetry.kotlin.k2j.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextFactory
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class SpanContextFactoryImpl : SpanContextFactory {
    override fun invalid(): SpanContext = SpanContext.Companion.invalid()

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext = SpanContext.Companion.create(traceId, spanId, traceFlags, traceState)
}
