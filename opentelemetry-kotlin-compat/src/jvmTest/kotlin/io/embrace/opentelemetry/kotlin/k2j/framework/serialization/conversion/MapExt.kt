package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

internal fun Map<String, Any>.toStringMap() = mapValues { it.value.toString() }
