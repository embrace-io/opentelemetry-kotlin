package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@ExperimentalApi
internal object NoopSpanContext : SpanContext {
    override val traceId: String = ""
    override val spanId: String = ""
    override val traceFlags: TraceFlags = NoopTraceFlags
    override val isValid: Boolean = false
    override val isRemote: Boolean = false
    override val traceState: TraceState = NoopTraceState
}
