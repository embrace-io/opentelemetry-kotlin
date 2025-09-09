package io.embrace.opentelemetry.kotlin.resource

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal class ResourceImpl(
    override val attributes: Map<String, Any>,
    override val schemaUrl: String?,
) : Resource {

    override fun asNewResource(action: MutableResource.() -> Unit): Resource {
        val impl = MutableResourceImpl(attributes.toMutableMap(), schemaUrl)
        impl.apply(action)
        return ResourceImpl(impl.attributes.toMap(), impl.schemaUrl)
    }
}
