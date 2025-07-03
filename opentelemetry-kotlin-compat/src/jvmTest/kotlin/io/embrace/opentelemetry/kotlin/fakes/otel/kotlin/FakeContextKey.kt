package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.ContextKey

@OptIn(ExperimentalApi::class)
internal class FakeContextKey<T>(
    override val name: String = "key"
) : ContextKey<T>
