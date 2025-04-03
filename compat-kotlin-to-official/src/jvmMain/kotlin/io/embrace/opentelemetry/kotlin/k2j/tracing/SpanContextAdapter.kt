package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.TraceState
import io.embrace.opentelemetry.kotlin.tracing.TraceStateMutator

internal class SpanContextAdapter(
    private val spanContext: OtelJavaSpanContext
) : SpanContext {

    override val traceId: String = spanContext.traceId
    override val spanId: String = spanContext.spanId
    override val traceFlags: TraceFlags = TraceFlagsAdapter(spanContext.traceFlags)
    override val isValid: Boolean = spanContext.isValid
    override val isRemote: Boolean = spanContext.isRemote

    override fun updateTraceState(action: TraceStateMutator.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getTraceState(): TraceState {
        return TraceStateAdapter(spanContext.traceState)
    }
}
