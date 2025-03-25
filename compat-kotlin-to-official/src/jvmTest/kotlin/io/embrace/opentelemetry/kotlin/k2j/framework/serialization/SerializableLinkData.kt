package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.LinkData

internal fun LinkData.toSerializable() = SerializableLinkData(
    spanContext = spanContext.toSerializable(),
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size(),
)
