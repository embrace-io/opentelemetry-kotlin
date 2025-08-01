package io.embrace.opentelemetry.kotlin.j2k.bridge.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context

@OptIn(ExperimentalApi::class)
public fun OtelJavaContext.toOtelKotlinContext(): Context {
    return OtelJavaContextAdapter(this)
}
