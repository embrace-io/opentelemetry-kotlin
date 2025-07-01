package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableLinkData

internal fun OtelJavaLinkData.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableLinkData(
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size(),
)
