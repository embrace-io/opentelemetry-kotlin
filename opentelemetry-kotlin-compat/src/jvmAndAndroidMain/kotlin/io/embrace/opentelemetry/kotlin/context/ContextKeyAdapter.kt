package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey

@ExperimentalApi
internal class ContextKeyAdapter<T>(
    internal val impl: OtelJavaContextKey<T>
) : ContextKey<T> {
    override val name: String = impl.toString()
}
