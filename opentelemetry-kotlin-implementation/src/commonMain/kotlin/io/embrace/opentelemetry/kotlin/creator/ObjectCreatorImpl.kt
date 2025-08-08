package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ObjectCreatorImpl : ObjectCreator {

    override val spanContext: SpanContextCreator
        get() = throw UnsupportedOperationException()

    override val traceFlags: TraceFlagsCreator
        get() = throw UnsupportedOperationException()

    override val traceState: TraceStateCreator
        get() = throw UnsupportedOperationException()

    override val context: ContextCreator
        get() = throw UnsupportedOperationException()

    override val span: SpanCreator
        get() = throw UnsupportedOperationException()

    override val idCreator: TracingIdCreator
        get() = throw UnsupportedOperationException()
}
