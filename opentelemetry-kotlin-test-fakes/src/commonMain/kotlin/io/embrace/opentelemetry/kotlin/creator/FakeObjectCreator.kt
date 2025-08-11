package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator
        get() = throw UnsupportedOperationException()
    override val traceFlags: TraceFlagsCreator
        get() = throw UnsupportedOperationException()
    override val traceState: TraceStateCreator
        get() = throw UnsupportedOperationException()
    override val context: ContextCreator = FakeContextCreator()
    override val span: SpanCreator
        get() = throw UnsupportedOperationException()
    override val idCreator: TracingIdCreator
        get() = throw UnsupportedOperationException()
}
