package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.opentelemetry.kotlin.tracing.model.TraceState
import io.opentelemetry.kotlin.tracing.model.TraceStateAdapter

@OptIn(ExperimentalApi::class)
internal class CompatTraceStateCreator : TraceStateCreator {
    override val default: TraceState by lazy { TraceStateAdapter(OtelJavaTraceState.getDefault()) }
}
