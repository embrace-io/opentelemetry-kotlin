package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.EventData

internal fun EventData.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toSerializable(),
    timestampNs = epochNanos,
    totalAttributesCount = attributes.size(),
)
