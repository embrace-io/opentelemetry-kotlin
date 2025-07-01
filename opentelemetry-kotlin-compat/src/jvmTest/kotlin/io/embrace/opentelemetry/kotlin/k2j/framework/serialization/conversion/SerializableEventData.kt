package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableEventData

internal fun OtelJavaEventData.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toSerializable(),
    timestampNs = epochNanos,
    totalAttributesCount = attributes.size(),
)
