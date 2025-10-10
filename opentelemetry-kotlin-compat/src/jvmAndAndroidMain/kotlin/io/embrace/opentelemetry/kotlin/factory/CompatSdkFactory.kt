package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class CompatSdkFactory(
    override val tracingIdFactory: TracingIdFactory = CompatTracingIdFactory()
) : SdkFactory {
    override val spanContextFactory: SpanContextFactory by lazy { CompatSpanContextFactory() }
    override val traceFlagsFactory: TraceFlagsFactory by lazy { CompatTraceFlagsFactory() }
    override val traceStateFactory: TraceStateFactory by lazy { CompatTraceStateFactory() }
    override val contextFactory: ContextFactory by lazy { CompatContextFactory() }
    override val spanFactory: SpanFactory by lazy { CompatSpanFactory(spanContextFactory) }
}
