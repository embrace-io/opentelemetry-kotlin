package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.NoopSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal object NoopSpanContextFactory : SpanContextFactory {

    override val invalid: SpanContext = NoopSpanContext

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext = NoopSpanContext

    override fun create(
        traceIdBytes: ByteArray,
        spanIdBytes: ByteArray,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext = NoopSpanContext
}
