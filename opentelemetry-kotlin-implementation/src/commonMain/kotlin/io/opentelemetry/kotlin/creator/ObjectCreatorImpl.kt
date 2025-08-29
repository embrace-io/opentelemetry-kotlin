package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ObjectCreatorImpl(
    override val idCreator: TracingIdCreator = TracingIdCreatorImpl()
) : ObjectCreator {

    override val spanContext: SpanContextCreator = SpanContextCreatorImpl()

    override val traceFlags: TraceFlagsCreator
        get() = TraceFlagsCreatorImpl()

    override val traceState: TraceStateCreator
        get() = TraceStateCreatorImpl()

    private val contextCreatorImpl = ContextCreatorImpl()
    override val context: ContextCreator = contextCreatorImpl

    override val span: SpanCreator = SpanCreatorImpl(spanContext, contextCreatorImpl.spanKey)
}
