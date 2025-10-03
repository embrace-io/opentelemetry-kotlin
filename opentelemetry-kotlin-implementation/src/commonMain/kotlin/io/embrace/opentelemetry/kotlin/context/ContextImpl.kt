package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ContextImpl(private val impl: Map<ContextKey<*>, Any?> = emptyMap()) : Context {

    override fun <T> createKey(name: String): ContextKey<T> = ContextKeyImpl(name)

    override fun <T> set(
        key: ContextKey<T>,
        value: T?
    ): Context {
        val newValues = impl.plus(Pair(key, value))
        return ContextImpl(newValues)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: ContextKey<T>): T? {
        return impl[key] as? T
    }

    override fun attach(): Scope { // FIXME
        throw UnsupportedOperationException()
    }
}
