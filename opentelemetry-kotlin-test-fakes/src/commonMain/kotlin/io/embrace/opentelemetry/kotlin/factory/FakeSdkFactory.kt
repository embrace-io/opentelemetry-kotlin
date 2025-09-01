package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeSdkFactory : SdkFactory {
    override val spanContext: SpanContextFactory = FakeSpanContextFactory()
    override val traceFlags: TraceFlagsFactory = FakeTraceFlagsFactory()
    override val traceState: TraceStateFactory = FakeTraceStateFactory()
    override val context: ContextFactory = FakeContextFactory()
    override val span: SpanFactory = FakeSpanFactory()
    override val tracingIds: TracingIdFactory = FakeTracingIdFactory()
}
