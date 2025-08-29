package io.opentelemetry.kotlin.init

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.DEFAULT_ATTRIBUTE_LIMIT
import io.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.opentelemetry.kotlin.resource.Resource
import io.opentelemetry.kotlin.resource.ResourceImpl

@OptIn(ExperimentalApi::class)
internal class ResourceConfigImpl : ResourceConfigDsl {

    private val resourceAttrs = MutableAttributeContainerImpl(DEFAULT_ATTRIBUTE_LIMIT)
    private var schemaUrl: String? = null

    override fun resource(
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        this.schemaUrl = schemaUrl
        resourceAttrs.attributes()
    }

    fun generateResource(): Resource = ResourceImpl(
        schemaUrl = schemaUrl,
        attributes = resourceAttrs.attributes
    )
}
