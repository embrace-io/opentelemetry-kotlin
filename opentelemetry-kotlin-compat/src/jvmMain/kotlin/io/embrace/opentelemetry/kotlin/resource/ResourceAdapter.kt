package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.attributes.toMap

@OptIn(ExperimentalApi::class)
internal class ResourceAdapter(
    impl: OtelJavaResource
) : Resource {
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val schemaUrl: String? = impl.schemaUrl
}
