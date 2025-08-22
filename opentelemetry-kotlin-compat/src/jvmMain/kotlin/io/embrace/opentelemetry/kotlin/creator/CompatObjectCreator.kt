package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class CompatObjectCreator(
    override val idCreator: TracingIdCreator = CompatTracingIdCreator()
) : ObjectCreator {
    override val spanContext: SpanContextCreator by lazy { CompatSpanContextCreator() }
    override val traceFlags: TraceFlagsCreator by lazy { CompatTraceFlagsCreator() }
    override val traceState: TraceStateCreator by lazy { CompatTraceStateCreator() }
    override val context: ContextCreator by lazy { CompatContextCreator() }
    override val span: SpanCreator by lazy { CompatSpanCreator(spanContext) }
}
