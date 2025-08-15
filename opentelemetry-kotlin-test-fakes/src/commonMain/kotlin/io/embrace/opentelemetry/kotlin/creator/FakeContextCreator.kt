package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.FakeContext
import io.embrace.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal class FakeContextCreator : ContextCreator {
    override fun root(): Context = FakeContext()
    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = FakeContext()
}
