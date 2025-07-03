package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextKey

@OptIn(ExperimentalApi::class)
internal class FakeContext(
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
