package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes

internal fun OtelJavaAttributes.toMap(): Map<String, Any> {
    return this.asMap().mapKeys { it.key.key }
}
