package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator = NoopSpanContextCreator
    override val traceFlags: TraceFlagsCreator = NoopTraceFlagsCreator
    override val traceState: TraceStateCreator = NoopTraceStateCreator
    override val context: ContextCreator = NoopContextCreator
    override val span: SpanCreator = NoopSpanCreator
    override val idCreator: TracingIdCreator = NoopTracingIdCreator
}
