package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaContext
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaContextKey

@ExperimentalApi
internal class ContextAdapter(
    private val impl: Context
) : OtelJavaContext {

    override fun <V : Any?> get(key: OtelJavaContextKey<V>): V? {
        return impl.getValue(ContextKeyAdapter(key))
    }

    override fun <V : Any?> with(key: OtelJavaContextKey<V>, value: V): OtelJavaContext {
        val ctx = impl.setValue(ContextKeyAdapter(key), value)
        return ContextAdapter(ctx)
    }
}
