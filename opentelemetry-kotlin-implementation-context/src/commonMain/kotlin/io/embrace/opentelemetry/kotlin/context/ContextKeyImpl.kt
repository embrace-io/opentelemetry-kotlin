package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
public class ContextKeyImpl<T>(override val name: String) : ContextKey<T>
