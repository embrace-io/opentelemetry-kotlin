package io.embrace.opentelemetry.kotlin.j2k.bridge.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyAdapter
import java.util.concurrent.ConcurrentHashMap

@OptIn(ExperimentalApi::class)
internal class OtelJavaContextKeyRepository {

    // TODO: future: cleanup stale references
    // TODO: future: support nullable values

    companion object {
        val INSTANCE = OtelJavaContextKeyRepository()
    }

    private val impl = ConcurrentHashMap<ContextKey<*>, OtelJavaContextKey<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ContextKey<T>): OtelJavaContextKey<T> {
        return impl.getOrPut(key) {
            (key as ContextKeyAdapter).impl
        } as OtelJavaContextKey<T>
    }
}
