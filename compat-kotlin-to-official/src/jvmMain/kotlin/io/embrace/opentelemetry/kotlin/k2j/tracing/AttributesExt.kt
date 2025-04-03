package io.embrace.opentelemetry.kotlin.k2j.tracing

internal fun OtelJavaAttributes.toMap(): Map<String, Any> {
    return this.asMap().mapKeys { it.key.key }
}
