package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
class FakeContextKey<T>(
    override val name: String = "key"
) : ContextKey<T>
