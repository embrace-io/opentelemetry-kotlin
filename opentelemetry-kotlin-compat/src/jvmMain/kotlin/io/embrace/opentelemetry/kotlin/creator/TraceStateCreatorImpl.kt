package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.k2j.tracing.TraceStateAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class TraceStateCreatorImpl : TraceStateCreator {
    override val default: TraceState by lazy { TraceStateAdapter(OtelJavaTraceState.getDefault()) }
}
