package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.opentelemetry.api.common.Attributes

internal fun Attributes.toMap(): Map<String, Any> {
    return this.asMap().mapKeys { it.key.key }
}
