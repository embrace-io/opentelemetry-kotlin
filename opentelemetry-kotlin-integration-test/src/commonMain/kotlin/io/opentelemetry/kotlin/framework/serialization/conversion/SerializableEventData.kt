package io.opentelemetry.kotlin.framework.serialization.conversion

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.framework.serialization.SerializableEventData
import io.opentelemetry.kotlin.tracing.data.EventData
import io.opentelemetry.kotlin.framework.serialization.conversion.toSerializable

@OptIn(ExperimentalApi::class)
fun EventData.toSerializable() =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableEventData(
        name = name,
        attributes = attributes.toSerializable(),
        timestamp = timestamp,
        totalAttributesCount = attributes.size,
    )
