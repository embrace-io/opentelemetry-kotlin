package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableLinkData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData

@OptIn(ExperimentalApi::class)
fun LinkData.toSerializable(sanitizeSpanContextIds: Boolean = true) = SerializableLinkData(
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size,
)
