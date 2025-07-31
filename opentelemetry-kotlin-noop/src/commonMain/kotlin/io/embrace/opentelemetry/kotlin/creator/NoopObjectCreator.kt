package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator = NoopSpanContextCreator
    override val traceFlags: TraceFlagsCreator = NoopTraceFlagsCreator
    override val traceState: TraceStateCreator = NoopTraceStateCreator
    override val context: ContextCreator = NoopContextCreator
}
