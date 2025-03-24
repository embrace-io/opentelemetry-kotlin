package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.resources.Resource
import kotlinx.serialization.Serializable

internal fun Resource.toSerializable() = SerializableResource(
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)

@Serializable
internal data class SerializableResource(
    val schemaUrl: String,
    val attributes: Map<String, String>,
)
