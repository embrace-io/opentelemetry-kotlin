package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeContext(
    val attrs: Map<ContextKey<*>, Any?> = emptyMap(),
) : Context {

    override fun <T> createKey(name: String): ContextKey<T> = FakeContextKey(name)

    override fun <T> set(key: ContextKey<T>, value: T?): Context {
        return FakeContext(attrs + (key to value))
    }

    override fun <T> get(key: ContextKey<T>): T? {
        return null
    }
}
