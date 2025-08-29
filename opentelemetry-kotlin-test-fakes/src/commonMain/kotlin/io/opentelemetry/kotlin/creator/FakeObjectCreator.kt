package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator = FakeSpanContextCreator()
    override val traceFlags: TraceFlagsCreator = FakeTraceFlagsCreator()
    override val traceState: TraceStateCreator = FakeTraceStateCreator()
    override val context: ContextCreator = FakeContextCreator()
    override val span: SpanCreator = FakeSpanCreator()
    override val idCreator: TracingIdCreator = FakeTracingIdCreator()
}
