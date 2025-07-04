package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKey

@ExperimentalApi
internal class ContextKeyAdapter<T>(
    internal val impl: OtelJavaContextKey<T>
) : ContextKey<T> {
    override val name: String = impl.toString()
}
