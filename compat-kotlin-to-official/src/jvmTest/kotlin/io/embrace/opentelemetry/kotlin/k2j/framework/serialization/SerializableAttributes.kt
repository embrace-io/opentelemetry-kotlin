package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes

internal fun OtelJavaAttributes.toSerializable(): Map<String, String> = asMap().map {
    Pair(it.key.key, it.value.toString())
}.toMap()
