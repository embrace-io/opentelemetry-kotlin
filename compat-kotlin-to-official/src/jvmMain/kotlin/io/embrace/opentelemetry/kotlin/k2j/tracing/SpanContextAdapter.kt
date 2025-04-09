package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.k2j.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.TraceState
import io.embrace.opentelemetry.kotlin.tracing.TraceStateMutator

internal class SpanContextAdapter(
    val impl: OtelJavaSpanContext
) : SpanContext {

    override val traceId: String = impl.traceId
    override val spanId: String = impl.spanId
    override val traceFlags: TraceFlags = TraceFlagsAdapter(impl.traceFlags)
    override val isValid: Boolean = impl.isValid
    override val isRemote: Boolean = impl.isRemote

    override fun updateTraceState(action: TraceStateMutator.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getTraceState(): TraceState {
        return TraceStateAdapter(impl.traceState)
    }
}
