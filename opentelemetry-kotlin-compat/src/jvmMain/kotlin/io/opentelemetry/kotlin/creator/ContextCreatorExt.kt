package io.opentelemetry.kotlin.creator

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaContext
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.toOtelKotlinContext

/**
 * Retrieves the current Context.
 */
@OptIn(ExperimentalApi::class)
public fun ContextCreator.current(): Context = OtelJavaContext.current().toOtelKotlinContext()
