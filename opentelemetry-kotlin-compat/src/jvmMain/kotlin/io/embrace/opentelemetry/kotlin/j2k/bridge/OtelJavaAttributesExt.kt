package io.embrace.opentelemetry.kotlin.j2k.bridge

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.resource.Resource

internal fun attrsFromMap(map: Map<String, Any>): OtelJavaAttributes {
    val builder = OtelJavaAttributes.builder()
    map.forEach {
        builder.put(it.key, it.value.toString())
    }
    return builder.build()
}

@OptIn(ExperimentalApi::class)
internal fun resourceFromMap(resource: Resource): OtelJavaResource {
    val map = resource.attributes
    val schemaUrl = resource.schemaUrl
    return OtelJavaResource.create(
        attrsFromMap(map),
        schemaUrl
    )
}
