package io.opentelemetry.kotlin.context

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaContextKey
import java.util.Collections
import java.util.WeakHashMap

@OptIn(ExperimentalApi::class)
internal class ContextKeyRepository {

    companion object {
        val INSTANCE = ContextKeyRepository()
    }

    private val impl = Collections.synchronizedMap(WeakHashMap<ContextKey<*>, OtelJavaContextKey<*>>())

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ContextKey<T>): OtelJavaContextKey<T> {
        return impl.getOrPut(key) {
            (key as ContextKeyAdapter).impl
        } as OtelJavaContextKey<T>
    }
}
