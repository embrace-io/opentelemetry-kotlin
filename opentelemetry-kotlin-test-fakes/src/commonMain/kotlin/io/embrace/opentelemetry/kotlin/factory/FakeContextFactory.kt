package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal class FakeContextFactory : ContextFactory {

    override fun root(): Context = FakeContext()

    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = FakeContext()

    override fun implicitContext(): Context = FakeContext()
}
