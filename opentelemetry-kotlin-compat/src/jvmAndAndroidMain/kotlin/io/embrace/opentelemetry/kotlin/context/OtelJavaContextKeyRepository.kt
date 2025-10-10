package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import java.util.Collections
import java.util.WeakHashMap

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalApi::class)
internal class OtelJavaContextKeyRepository {

    companion object {
        val INSTANCE = OtelJavaContextKeyRepository()
    }

    private val impl = Collections.synchronizedMap(WeakHashMap<OtelJavaContextKey<*>, ContextKey<*>>())

    fun <T> get(key: OtelJavaContextKey<T>): ContextKey<T> {
        return impl.getOrPut(key) {
            ContextKeyAdapter(key)
        } as ContextKey<T>
    }
}
