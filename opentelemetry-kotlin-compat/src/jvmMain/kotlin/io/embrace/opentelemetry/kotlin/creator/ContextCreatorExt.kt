package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.j2k.bridge.context.toOtelKotlinContext

/**
 * Retrieves the current Context.
 */
@OptIn(ExperimentalApi::class)
public fun ContextCreator.current(): Context = OtelJavaContext.current().toOtelKotlinContext()
