package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.Context

@ExperimentalApi
internal class ContextAdapter(
    private val impl: Context,
    private val repository: ContextKeyRepository,
) : OtelJavaContext {

    override fun <V : Any?> get(key: OtelJavaContextKey<V>): V? {
        return impl[repository.get(key)]
    }

    override fun <V : Any?> with(key: OtelJavaContextKey<V>, value: V): OtelJavaContext {
        val ctx = impl.set(repository.get(key), value)
        return ContextAdapter(ctx, repository)
    }
}
