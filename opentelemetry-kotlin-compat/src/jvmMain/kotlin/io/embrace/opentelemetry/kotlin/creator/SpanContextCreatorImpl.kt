package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TraceFlagsAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TraceStateAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.convertToOtelJava
import io.embrace.opentelemetry.kotlin.tracing.SpanContextImpl
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class SpanContextCreatorImpl : SpanContextCreator {

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
            traceFlags.convertToOtelJava(),
            traceState.convertToOtelJava()
        )
    )
}
