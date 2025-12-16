package io.opentelemetry.kotlin.framework.serialization.conversion

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.framework.serialization.SerializableLinkData
import io.opentelemetry.kotlin.tracing.data.LinkData
import io.opentelemetry.kotlin.framework.serialization.conversion.toSerializable

@OptIn(ExperimentalApi::class)
fun LinkData.toSerializable() =
    _root_ide_package_.io.opentelemetry.kotlin.framework.serialization.SerializableLinkData(
        spanContext = spanContext.toSerializable(),
        attributes = attributes.toSerializable(),
        totalAttributeCount = attributes.size,
    )
