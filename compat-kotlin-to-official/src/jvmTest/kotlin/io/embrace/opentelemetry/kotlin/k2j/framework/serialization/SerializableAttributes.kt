package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.api.common.Attributes

internal fun Attributes.toSerializable(): Map<String, String> = asMap().map {
    Pair(it.key.key, it.value.toString())
}.toMap()
