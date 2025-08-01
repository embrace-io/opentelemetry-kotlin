@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.k2j.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.j2k.bridge.context.OtelJavaContextAdapter

@OptIn(ExperimentalApi::class)
public fun Context.toOtelJava(): OtelJavaContext {
    return (this as? OtelJavaContextAdapter)?.impl ?: ContextAdapter(this)
}
