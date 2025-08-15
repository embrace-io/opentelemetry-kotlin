package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableEventData
import io.embrace.opentelemetry.kotlin.tracing.data.EventData

@OptIn(ExperimentalApi::class)
fun EventData.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toSerializable(),
    timestamp = timestamp,
    totalAttributesCount = attributes.size,
)
