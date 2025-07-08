package io.embrace.opentelemetry.kotlin.j2k.bridge.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyAdapter
import java.util.Collections
import java.util.WeakHashMap

@OptIn(ExperimentalApi::class)
internal class OtelJavaContextKeyRepository {

    companion object {
        val INSTANCE = OtelJavaContextKeyRepository()
    }

    private val impl = Collections.synchronizedMap(WeakHashMap<ContextKey<*>, OtelJavaContextKey<*>>())

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: ContextKey<T>): OtelJavaContextKey<T> {
        return impl.getOrPut(key) {
            (key as ContextKeyAdapter).impl
        } as OtelJavaContextKey<T>
    }
}
