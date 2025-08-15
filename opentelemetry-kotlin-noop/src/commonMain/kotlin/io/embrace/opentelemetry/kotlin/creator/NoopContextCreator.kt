package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.NoopContext
import io.embrace.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal object NoopContextCreator : ContextCreator {
    override fun root(): Context = NoopContext
    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = NoopContext
}
