package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaContext
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.toOtelKotlinContext
import io.opentelemetry.kotlin.tracing.ext.storeInContext
import io.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal class CompatContextCreator : ContextCreator {

    override fun root(): Context = OtelJavaContext.root().toOtelKotlinContext()

    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = span.storeInContext(context)
}
