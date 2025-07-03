package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
public class SpanContextAdapter( // temporarily public, will be internal in future
    public val impl: OtelJavaSpanContext
) : SpanContext {
    override val traceId: String = impl.traceId
    override val spanId: String = impl.spanId
    override val traceFlags: TraceFlags = TraceFlagsAdapter(impl.traceFlags)
    override val isValid: Boolean = impl.isValid
    override val isRemote: Boolean = impl.isRemote
    override val traceState: TraceState = TraceStateAdapter(impl.traceState)
}
