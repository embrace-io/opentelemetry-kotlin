package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextImpl
import io.embrace.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
public class ContextFactoryImpl : ContextFactory {

    private val root = ContextImpl()
    internal val spanKey = root.createKey<Span>("opentelemetry-kotlin-span")

    override fun root(): Context = root

    override fun storeSpan(context: Context, span: Span): Context {
        return context.set(spanKey, span)
    }
}
