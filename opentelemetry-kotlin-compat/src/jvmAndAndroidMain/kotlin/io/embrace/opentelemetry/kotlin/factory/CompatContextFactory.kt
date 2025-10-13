package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.toOtelKotlinContext
import io.embrace.opentelemetry.kotlin.tracing.ext.storeInContext
import io.embrace.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal class CompatContextFactory : ContextFactory {

    override fun root(): Context = OtelJavaContext.root().toOtelKotlinContext()

    override fun storeSpan(
        context: Context,
        span: Span
    ): Context = span.storeInContext(context)

    override fun implicitContext(): Context = OtelJavaContext.root().toOtelKotlinContext()
}
