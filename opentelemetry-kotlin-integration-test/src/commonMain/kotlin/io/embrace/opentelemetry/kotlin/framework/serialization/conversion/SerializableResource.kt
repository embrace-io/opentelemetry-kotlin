package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableResource
import io.embrace.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
fun Resource.toSerializable() = SerializableResource(
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)
