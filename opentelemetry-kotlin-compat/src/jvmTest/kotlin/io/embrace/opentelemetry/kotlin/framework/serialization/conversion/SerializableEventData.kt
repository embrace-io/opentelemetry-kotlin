package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableEventData

internal fun OtelJavaEventData.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toSerializable(),
    timestamp = epochNanos,
    totalAttributesCount = attributes.size(),
)
