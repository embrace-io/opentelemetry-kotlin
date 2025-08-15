package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ObjectCreatorImpl : ObjectCreator {

    override val spanContext: SpanContextCreator = SpanContextCreatorImpl()

    override val traceFlags: TraceFlagsCreator
        get() = TraceFlagsCreatorImpl()

    override val traceState: TraceStateCreator
        get() = TraceStateCreatorImpl()

    private val contextCreatorImpl = ContextCreatorImpl()
    override val context: ContextCreator = contextCreatorImpl

    override val span: SpanCreator = SpanCreatorImpl(spanContext, contextCreatorImpl.spanKey)

    override val idCreator: TracingIdCreator = TracingIdCreatorImpl()
}
