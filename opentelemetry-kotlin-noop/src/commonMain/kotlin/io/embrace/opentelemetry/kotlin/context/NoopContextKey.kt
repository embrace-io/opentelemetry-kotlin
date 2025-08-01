package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class NoopContextKey<T> : ContextKey<T> {
    override val name: String = ""
}
