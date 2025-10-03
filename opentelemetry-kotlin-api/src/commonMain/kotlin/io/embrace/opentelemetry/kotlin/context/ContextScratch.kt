@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

// TODO: add to SdkFactory
internal lateinit var contextStorageImpl: ImplicitContextStorage

// TODO: provide way to set ContextStorage when creating OpenTelemetry instance
internal fun exampleUsage(api: OpenTelemetry) {
    // obtain the current context. Defaults to root() if no implicit context is available.
    val ctx = api.contextFactory.implicitContext()

    // create a new context but don't set it as the implicit context.
    val newContext = ctx.with(mapOf("key" to "value"))

    // set the new context as the implicit context.
    val curr = newContext.attach()

    // unset the new context as the implicit context and restore the previous context.
    curr.detach()

    // sets the new context as the implicit context, perform an operation, then unsets it.
    ctx.executeInContext {
        // ...
    }

    // creates a new context with the provided attributes. sets this new context as the
    // implicit context, perform an operation, then unsets it.
    ctx.executeInContext(mapOf("key" to "value")) {
        // ...
    }
}
