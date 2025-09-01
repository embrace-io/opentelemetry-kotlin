package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class CompatSdkFactory(
    override val tracingIds: TracingIdFactory = CompatTracingIdFactory()
) : SdkFactory {
    override val spanContext: SpanContextFactory by lazy { CompatSpanContextFactory() }
    override val traceFlags: TraceFlagsFactory by lazy { CompatTraceFlagsFactory() }
    override val traceState: TraceStateFactory by lazy { CompatTraceStateFactory() }
    override val context: ContextFactory by lazy { CompatContextFactory() }
    override val span: SpanFactory by lazy { CompatSpanFactory(spanContext) }
}
