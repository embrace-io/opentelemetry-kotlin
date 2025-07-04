package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.SerializableResource
import io.embrace.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
internal fun Resource.toSerializable() = SerializableResource(
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toStringMap()
)
