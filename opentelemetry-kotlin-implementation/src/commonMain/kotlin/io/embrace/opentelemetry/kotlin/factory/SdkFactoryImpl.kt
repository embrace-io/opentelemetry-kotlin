package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class SdkFactoryImpl(
    override val tracingIdFactory: TracingIdFactory = TracingIdFactoryImpl()
) : SdkFactory {

    override val spanContextFactory: SpanContextFactory = SpanContextFactoryImpl()

    override val traceFlagsFactory: TraceFlagsFactory
        get() = TraceFlagsFactoryImpl()

    override val traceStateFactory: TraceStateFactory
        get() = TraceStateFactoryImpl()

    private val contextFactoryImpl = ContextFactoryImpl()
    override val contextFactory: ContextFactory = contextFactoryImpl

    override val spanFactory: SpanFactory = SpanFactoryImpl(spanContextFactory, contextFactoryImpl.spanKey)
}
