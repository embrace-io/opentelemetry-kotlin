package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.NoopTraceState
import io.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal object NoopTraceStateCreator : TraceStateCreator {
    override val default: TraceState = NoopTraceState
}
