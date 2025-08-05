package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.resource.ResourceImpl

@OptIn(ExperimentalApi::class)
internal class ResourceConfigImpl : ResourceConfigDsl {

    private val resourceAttrs = MutableAttributeContainerImpl()
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
