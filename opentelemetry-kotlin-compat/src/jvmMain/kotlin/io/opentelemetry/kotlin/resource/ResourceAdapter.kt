package io.opentelemetry.kotlin.resource

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaResource
import io.opentelemetry.kotlin.attributes.toMap

@OptIn(ExperimentalApi::class)
internal class ResourceAdapter(
    impl: OtelJavaResource
) : Resource {
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val schemaUrl: String? = impl.schemaUrl
}
