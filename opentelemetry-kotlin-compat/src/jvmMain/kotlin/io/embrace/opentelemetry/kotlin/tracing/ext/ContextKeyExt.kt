package io.embrace.opentelemetry.kotlin.tracing.ext

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKeyAdapter

@OptIn(ExperimentalApi::class)
public fun <T> ContextKey<T>.toOtelJavaContextKey(): OtelJavaContextKey<T> = (this as ContextKeyAdapter).impl
