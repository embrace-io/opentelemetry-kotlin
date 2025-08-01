package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyAdapter

@OptIn(ExperimentalApi::class)
public fun <T> ContextKey<T>.toOtelJavaContextKey(): OtelJavaContextKey<T> = (this as ContextKeyAdapter).impl
