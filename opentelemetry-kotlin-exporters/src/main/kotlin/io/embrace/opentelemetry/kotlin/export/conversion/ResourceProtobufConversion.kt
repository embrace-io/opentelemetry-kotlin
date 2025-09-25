package io.embrace.opentelemetry.kotlin.export.conversion

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.opentelemetry.proto.resource.v1.resource

@OptIn(ExperimentalApi::class)
fun Resource.toProtobuf(): io.opentelemetry.proto.resource.v1.Resource {
    val record = this
    return resource {
        attributes.addAll(record.attributes.createKeyValues())
    }
}
