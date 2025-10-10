package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopContext : Context {

    override fun <T> createKey(name: String): ContextKey<T> = NoopContextKey()

    override fun <T> set(key: ContextKey<T>, value: T?): Context {
        return this
    }

    override fun <T> get(key: ContextKey<T>): T? {
        return null
    }

    override fun attach(): Scope = NoopScope
}
