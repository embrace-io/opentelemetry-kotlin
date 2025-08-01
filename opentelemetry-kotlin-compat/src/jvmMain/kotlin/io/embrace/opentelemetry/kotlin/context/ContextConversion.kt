@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext

@OptIn(ExperimentalApi::class)
public fun Context.toOtelJavaContext(): OtelJavaContext {
    return (this as? ContextAdapter)?.impl ?: OtelJavaContextAdapter(this)
}
