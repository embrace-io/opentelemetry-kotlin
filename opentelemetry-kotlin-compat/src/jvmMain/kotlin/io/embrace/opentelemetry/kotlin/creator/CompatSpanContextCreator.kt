package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanContextImpl
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaTraceState
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlagsAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceStateAdapter

@OptIn(ExperimentalApi::class)
internal class CompatSpanContextCreator : SpanContextCreator {

    override val invalid: SpanContext by lazy {
        val impl: OtelJavaSpanContext = OtelJavaSpanContext.getInvalid()

        SpanContextImpl(
            traceId = impl.traceId,
            spanId = impl.spanId,
            traceFlags = TraceFlagsAdapter(impl.traceFlags),
            isValid = impl.isValid,
            isRemote = impl.isRemote,
            traceState = TraceStateAdapter(impl.traceState)
        )
    }

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext = SpanContextAdapter(
        OtelJavaSpanContext.create(
            traceId,
            spanId,
            traceFlags.toOtelJavaTraceFlags(),
            traceState.toOtelJavaTraceState()
        )
    )
}
