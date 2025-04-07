package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
internal object NoopSpanContext : SpanContext {

    override val traceId: String = ""
    override val spanId: String = ""
    override val traceFlags: TraceFlags = NoopTraceFlags
    override val isValid: Boolean = false
    override val isRemote: Boolean = false

    override fun getTraceState(): TraceState = NoopTraceState

    override fun updateTraceState(action: TraceStateMutator.() -> Unit) {
    }

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState,
        origin: SpanContextOrigin
    ): SpanContext = NoopSpanContext
}
