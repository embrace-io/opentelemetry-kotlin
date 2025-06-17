package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaContextKey
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalApi::class)
internal class ContextKeyRepository {

    // TODO: future: cleanup stale references
    // TODO: future: support nullable values

    companion object {
        val INSTANCE = ContextKeyRepository()
    }

    private val impl = ConcurrentHashMap<OtelJavaContextKey<*>, ContextKey<*>>()

    fun <T> get(key: OtelJavaContextKey<T>): ContextKey<T> {
        return impl.getOrPut(key) {
            ContextKeyAdapter(key)
        } as ContextKey<T>
    }
}
