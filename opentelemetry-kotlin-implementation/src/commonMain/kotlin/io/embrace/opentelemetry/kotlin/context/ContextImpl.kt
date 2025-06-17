package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
public class ContextImpl(
    private val impl: Map<ContextKey<*>, Any> = mapOf()
) : Context {

    override fun <T> createKey(name: String): ContextKey<T> = ContextKeyImpl(name)

    override fun <T> set(
        key: ContextKey<T>,
        value: T?
    ): Context {
        val newCtx = when (value) {
            null -> impl.minus(key)
            else -> impl.plus(Pair(key, value as Any))
        }
        return ContextImpl(newCtx)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: ContextKey<T>): T? = impl[key] as T?
}
