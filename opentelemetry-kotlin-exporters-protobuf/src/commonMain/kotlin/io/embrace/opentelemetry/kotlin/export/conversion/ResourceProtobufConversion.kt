package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.resource.Resource

@OptIn(ExperimentalApi::class)
internal fun Resource.toProtobuf() =
    io.opentelemetry.proto.resource.v1.Resource(attributes = attributes.createKeyValues())
