package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Creates a new context using the supplied values and the parent context, attaches it as
 * the implicit context, executes the code block, then detaches the [Scope] and restores the
 * previous implicit context value, if any was set.
 */
@OptIn(ExperimentalApi::class)
public fun Context.executeInContext(
    values: Map<String, Any> = emptyMap(),
    action: () -> Unit
): Context {
    val ctx = with(values)
    val scope = ctx.attach()
    try { // TODO: test exceptions etc
        action()
    } finally {
        scope.detach()
    }
    return ctx
}

/**
 * Sets the supplied context as the current implicit context. A [Scope] object
 * is returned which must be closed. When the [Scope] is closed the previous implicit context
 * will be restored.
 */
@OptIn(ExperimentalApi::class)
public fun Context.attachImpl(): Scope {
    val prev = contextStorageImpl.implicitContext()
    contextStorageImpl.setImplicitContext(this)
    return ScopeImpl(prev, this, contextStorageImpl)
}
