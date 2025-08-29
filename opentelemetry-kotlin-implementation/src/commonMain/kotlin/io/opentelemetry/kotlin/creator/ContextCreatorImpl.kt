package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.ContextImpl
import io.opentelemetry.kotlin.tracing.model.Span

@OptIn(ExperimentalApi::class)
internal class ContextCreatorImpl : ContextCreator {

    private val root = ContextImpl()
    internal val spanKey = root.createKey<Span>("opentelemetry-kotlin-span")

    override fun root(): Context = root

    override fun storeSpan(context: Context, span: Span): Context {
        return context.set(spanKey, span)
    }
}
