package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaContextKey

@ExperimentalApi
internal class ContextKeyAdapter<T>(
    impl: OtelJavaContextKey<T>
) : ContextKey<T> {
    override val name: String = impl.toString()
}
