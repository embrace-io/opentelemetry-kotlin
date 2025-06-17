package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData

internal fun OtelJavaEventData.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toSerializable(),
    timestampNs = epochNanos,
    totalAttributesCount = attributes.size(),
)
