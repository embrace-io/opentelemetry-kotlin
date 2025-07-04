package io.embrace.opentelemetry.kotlin.j2k.bridge.context

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.context.Context

@OptIn(ExperimentalApi::class)
internal fun OtelJavaContext.toOtelKotlin(): Context {
    return OtelJavaContextAdapter(this)
}
