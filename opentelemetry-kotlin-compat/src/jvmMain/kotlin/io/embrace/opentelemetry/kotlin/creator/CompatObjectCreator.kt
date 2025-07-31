package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class CompatObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator by lazy { SpanContextCreatorImpl() }
    override val traceFlags: TraceFlagsCreator by lazy { TraceFlagsCreatorImpl() }
    override val traceState: TraceStateCreator by lazy { TraceStateCreatorImpl() }
    override val context: ContextCreator by lazy { ContextCreatorImpl() }
    override val span: SpanCreator by lazy { SpanCreatorImpl(spanContext) }
}
