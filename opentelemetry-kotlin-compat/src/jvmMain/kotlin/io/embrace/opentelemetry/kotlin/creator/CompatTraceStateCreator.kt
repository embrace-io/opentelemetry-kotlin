package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceStateAdapter

@OptIn(ExperimentalApi::class)
internal class CompatTraceStateCreator : TraceStateCreator {
    override val default: TraceState by lazy { TraceStateAdapter(OtelJavaTraceState.getDefault()) }
}
