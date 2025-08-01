package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
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
