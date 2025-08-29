package io.opentelemetry.kotlin.framework.serialization.conversion

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.framework.serialization.SerializableResource
import io.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
fun Resource.toSerializable() = SerializableResource(
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.toSerializable(),
)
