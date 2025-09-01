package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopSdkFactory : SdkFactory {
    override val spanContext: SpanContextFactory = NoopSpanContextFactory
    override val traceFlags: TraceFlagsFactory = NoopTraceFlagsFactory
    override val traceState: TraceStateFactory = NoopTraceStateFactory
    override val context: ContextFactory = NoopContextFactory
    override val span: SpanFactory = NoopSpanFactory
    override val tracingIds: TracingIdFactory = NoopTracingIdFactory
}
