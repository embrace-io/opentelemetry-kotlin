package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.toOtelKotlinContext

/**
 * Retrieves the current Context.
 */
@OptIn(ExperimentalApi::class)
public fun ContextFactory.current(): Context = OtelJavaContext.current().toOtelKotlinContext()
