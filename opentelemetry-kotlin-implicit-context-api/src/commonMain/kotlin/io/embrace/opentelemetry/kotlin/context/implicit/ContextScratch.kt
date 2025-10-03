@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.context.implicit

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.with
import io.embrace.opentelemetry.kotlin.factory.ContextFactory


/**
 * Defines the public API for implicit Context. The value of the implicit Context is determined by
 * the current unit of execution. For example, one approach to implicit Context is to store the
 * current Context as a ThreadLocal. Equally, a Context might be set implicitly for a coroutine,
 * or even individual functions.
 *
 * https://opentelemetry.io/docs/specs/otel/context/#optional-global-operations
 */

// retrieves the current implicit context
public fun ContextFactory.current(): Context = TODO()


// hold references to both current and previous context objects
@OptIn(ExperimentalStdlibApi::class)
public interface Scope: AutoCloseable {

    public val storage: ContextStorage

    public fun detach(): Unit = close()

    override fun close() {
        // restore previous context object on storage
    }
}


// is this a subset of Map<T, Context> where T is Thread, ThreadContextElement, or a String?

@OptIn(ExperimentalApi::class)
public interface ContextStorage { // could be either ThreadLocal or use ThreadContextElement for coroutines

    // stores the context as the current implicit context, and returns a Scope object that must be closed
    public fun store(context: Context): Scope

    // returns the current implicit context or root() if none is set
    public val context: Context
}


// creates a new context (if values are supplied), attaches it, executes code, then detaches it
public fun Context.with(values: Map<String, Any> = emptyMap(), action: () -> Unit): Context = TODO()

// sets object as the current implicit context.
// return value is the implicit context at end of function execution
public fun Context.attach(): Scope = TODO()

// stops the current object from being the current implicit context.
// return value is the implicit context at end of function execution
public fun Context.detach(): Context = TODO()


// TODO: provide way to set ContextStorage when creating OpenTelemetry instance
internal fun exampleUsage(api: OpenTelemetry) {

    // obtain the current context. Defaults to root() if no implicit context is available.
    val ctx = api.contextFactory.current()

    // create a new context but don't set it as the implicit context.
    val newContext = ctx.with(mapOf("key" to "value"))

    // set the new context as the implicit context.
    val curr = ctx.attach()

    // unset the new context as the implicit context and restore the previous context.
    curr.detach()
    curr.close()

    // sets the new context as the implicit context, perform an operation, then unsets it.
    ctx.with {
        // ...
    }

    // creates a new context with the provided attributes. sets this new context as the
    // implicit context, perform an operation, then unsets it.
    ctx.with(mapOf("key" to "value")) {
        // ...
    }
}
