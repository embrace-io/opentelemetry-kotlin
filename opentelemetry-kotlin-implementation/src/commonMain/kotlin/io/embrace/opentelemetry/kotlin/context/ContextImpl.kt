package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ContextImpl : Context {

    override fun <T> createKey(name: String): ContextKey<T> = ContextKeyImpl(name)

    override fun <T> set(
        key: ContextKey<T>,
        value: T?
    ): Context {
        throw UnsupportedOperationException()
    }

    override fun <T> get(key: ContextKey<T>): T? {
        throw UnsupportedOperationException()
    }
}
