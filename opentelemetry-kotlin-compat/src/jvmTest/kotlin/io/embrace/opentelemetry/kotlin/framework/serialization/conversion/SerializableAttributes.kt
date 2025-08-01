package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes

internal fun OtelJavaAttributes.toSerializable(): Map<String, String> = asMap().map {
    Pair(it.key.key, it.value.toString())
}.toMap()
