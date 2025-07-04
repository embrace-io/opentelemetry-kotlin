package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableLinkData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableLink

@OptIn(ExperimentalApi::class)
internal fun ReadableLink.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableLinkData(
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    attributes = attributes.toStringMap(),
    totalAttributeCount = attributes.size,
)
