package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.k2j.context.current

/**
 * Retrieves the current Context.
 */
@OptIn(ExperimentalApi::class)
public fun ContextCreator.current(): Context = Context.current()
