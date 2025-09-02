package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceStateAdapter

@OptIn(ExperimentalApi::class)
internal class CompatTraceStateFactory : TraceStateFactory {
    override val default: TraceState by lazy { TraceStateAdapter(OtelJavaTraceState.getDefault()) }
}
