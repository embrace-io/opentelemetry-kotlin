package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableEventData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent

@OptIn(ExperimentalApi::class)
internal fun ReadableSpanEvent.toSerializable() = SerializableEventData(
    name = name,
    attributes = attributes.toStringMap(),
    timestampNs = timestamp,
    totalAttributesCount = attributes.size,
)
