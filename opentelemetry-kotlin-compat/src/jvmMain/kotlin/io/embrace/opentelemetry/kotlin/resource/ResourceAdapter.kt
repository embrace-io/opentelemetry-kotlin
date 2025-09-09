package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResourceBuilder
import io.embrace.opentelemetry.kotlin.attributes.toMap

@OptIn(ExperimentalApi::class)
internal class ResourceAdapter(
    impl: OtelJavaResource
) : Resource {
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val schemaUrl: String? = impl.schemaUrl

    override fun asNewResource(action: MutableResource.() -> Unit): Resource {
        val impl = MutableResourceImpl(attributes.toMutableMap(), schemaUrl)
        impl.apply(action)

        val builder = OtelJavaResourceBuilder()
        impl.schemaUrl?.let(builder::setSchemaUrl)

        impl.attributes.forEach {
            builder.put(it.key, it.value.toString())
        }
        return ResourceAdapter(builder.build())
    }
}
