package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ObjectCreatorImpl : ObjectCreator {

    override val spanContext: SpanContextCreator = SpanContextCreatorImpl()

    override val traceFlags: TraceFlagsCreator
        get() = TraceFlagsCreatorImpl()

    override val traceState: TraceStateCreator
        get() = TraceStateCreatorImpl()

    override val context: ContextCreator = ContextCreatorImpl()

    override val span: SpanCreator
        get() = throw UnsupportedOperationException()

    override val idCreator: TracingIdCreator
        get() = throw UnsupportedOperationException()
}
