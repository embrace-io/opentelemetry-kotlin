package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.LinkData

internal fun LinkData.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableLinkData(
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size(),
)
