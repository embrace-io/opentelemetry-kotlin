package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.opentelemetry.sdk.resources.Resource

internal fun Resource.toSerializable() = SerializableResource(
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)
