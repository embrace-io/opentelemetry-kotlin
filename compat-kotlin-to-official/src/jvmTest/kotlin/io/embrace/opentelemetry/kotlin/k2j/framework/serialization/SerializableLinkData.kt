package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.trace.data.LinkData
import kotlinx.serialization.Serializable

internal fun LinkData.toSerializable() = SerializableLinkData(
    spanContext = spanContext.toSerializable(),
    attributes = attributes.toSerializable(),
    totalAttributeCount = attributes.size(),
)

@Serializable
internal data class SerializableLinkData(
    val spanContext: SerializableSpanContext,
    val attributes: Map<String, String>,
    val totalAttributeCount: Int,
)
