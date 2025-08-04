package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey

@OptIn(ExperimentalApi::class)
public fun <T> ContextKey<T>.toOtelJavaContextKey(): OtelJavaContextKey<T> = (this as ContextKeyAdapter).impl
