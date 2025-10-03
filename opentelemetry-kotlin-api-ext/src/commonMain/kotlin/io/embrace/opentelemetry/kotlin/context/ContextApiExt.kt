package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe

/**
 * This function returns a new immutable [Context] that contains the key-value pairs
 * in the supplied map.
 */
@ThreadSafe
@ExperimentalApi
public fun Context.with(values: Map<String, Any>): Context {
    var ctx = this
    values.forEach { (key, value) ->
        ctx = ctx.set(createKey(key), value)
    }
    return ctx
}
