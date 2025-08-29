package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.NoopContext
import io.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal object NoopContextCreator : ContextCreator {
    override fun root(): Context = NoopContext
    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = NoopContext
}
