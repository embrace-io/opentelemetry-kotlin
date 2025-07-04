package io.embrace.opentelemetry.kotlin.j2k.bridge.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyAdapter

@OptIn(ExperimentalApi::class)
internal class OtelJavaContextAdapter(
    private val impl: OtelJavaContext,
    private val repository: OtelJavaContextKeyRepository = OtelJavaContextKeyRepository.INSTANCE
) : Context {

    override fun <T> createKey(name: String): ContextKey<T> {
        return ContextKeyAdapter(OtelJavaContextKey.named(name))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> set(key: ContextKey<T>, value: T?): Context {
        val ctx = impl.with(repository.get(key), value as (T & Any))
        return OtelJavaContextAdapter(ctx, repository)
    }

    override fun <T> get(key: ContextKey<T>): T? {
        return impl[repository.get(key)]
    }
}
