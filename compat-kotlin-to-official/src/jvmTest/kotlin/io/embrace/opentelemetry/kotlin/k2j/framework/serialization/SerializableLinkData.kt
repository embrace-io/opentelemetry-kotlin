package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData

internal fun OtelJavaLinkData.toSerializable(sanitizeSpanContextIds: Boolean) = SerializableLinkData(
    spanContext = spanContext.toSerializable(sanitizeSpanContextIds),
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size(),
)
