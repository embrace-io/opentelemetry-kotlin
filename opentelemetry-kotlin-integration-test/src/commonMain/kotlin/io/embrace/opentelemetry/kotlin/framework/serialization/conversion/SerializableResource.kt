package io.embrace.opentelemetry.kotlin.framework.serialization.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.serialization.SerializableResource
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.semconv.TelemetryAttributes

@OptIn(ExperimentalApi::class)
fun Resource.toSerializable() = SerializableResource(
    schemaUrl = schemaUrl.toString(),
    attributes = attributes.mapValues {
        when (it.key) {
            TelemetryAttributes.TELEMETRY_SDK_VERSION -> "UNKNOWN"
            else -> it.value
        }
    }.toSerializable(),
)
