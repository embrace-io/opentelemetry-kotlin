package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.FakeTraceState
import io.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class FakeTraceStateCreator : TraceStateCreator {
    override val default: TraceState = FakeTraceState()
}
