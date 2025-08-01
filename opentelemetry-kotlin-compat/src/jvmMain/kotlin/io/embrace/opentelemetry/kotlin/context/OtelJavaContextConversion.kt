package io.embrace.opentelemetry.kotlin.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext

@OptIn(ExperimentalApi::class)
public fun OtelJavaContext.toOtelKotlinContext(): Context {
    return ContextAdapter(this)
}
