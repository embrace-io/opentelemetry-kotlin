package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class SdkFactoryImpl(
    override val tracingIds: TracingIdFactory = TracingIdFactoryImpl()
) : SdkFactory {

    override val spanContext: SpanContextFactory = SpanContextFactoryImpl()

    override val traceFlags: TraceFlagsFactory
        get() = TraceFlagsFactoryImpl()

    override val traceState: TraceStateFactory
        get() = TraceStateFactoryImpl()

    private val contextFactoryImpl = ContextFactoryImpl()
    override val context: ContextFactory = contextFactoryImpl

    override val span: SpanFactory = SpanFactoryImpl(spanContext, contextFactoryImpl.spanKey)
}
