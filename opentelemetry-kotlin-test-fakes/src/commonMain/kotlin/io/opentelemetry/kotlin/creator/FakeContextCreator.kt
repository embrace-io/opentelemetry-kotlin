package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.FakeContext
import io.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal class FakeContextCreator : ContextCreator {
    override fun root(): Context = FakeContext()
    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = FakeContext()
}
