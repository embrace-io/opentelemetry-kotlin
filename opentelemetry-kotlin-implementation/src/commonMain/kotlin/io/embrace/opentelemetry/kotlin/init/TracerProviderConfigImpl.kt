package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.init.config.SpanLimitConfig
import io.embrace.opentelemetry.kotlin.init.config.TracingConfig
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.resource.ResourceImpl
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

@OptIn(ExperimentalApi::class)
internal class TracerProviderConfigImpl : TracerProviderConfigDsl {

    private val processors: MutableList<SpanProcessor> = mutableListOf()
    private val spanLimitsConfigImpl = SpanLimitsConfigImpl()
    private val resourceAttrs = MutableAttributeContainerImpl()
    private var schemaUrl: String? = null

    override fun spanLimits(action: SpanLimitsConfigDsl.() -> Unit) {
        spanLimitsConfigImpl.action()
    }

    override fun addSpanProcessor(processor: SpanProcessor) {
        processors.add(processor)
    }

    override fun resource(
        schemaUrl: String?,
        attributes: MutableAttributeContainer.() -> Unit,
    ) {
        this.schemaUrl = schemaUrl
        resourceAttrs.attributes()
    }

    fun generateTracingConfig(): TracingConfig = TracingConfig(
        processors = processors.toList(),
        spanLimits = generateSpanLimitsConfig(),
        resource = generateResource(),
    )

    private fun generateSpanLimitsConfig(): SpanLimitConfig = SpanLimitConfig(
        attributeCountLimit = spanLimitsConfigImpl.attributeCountLimit,
        linkCountLimit = spanLimitsConfigImpl.linkCountLimit,
        eventCountLimit = spanLimitsConfigImpl.eventCountLimit,
        attributeCountPerEventLimit = spanLimitsConfigImpl.attributeCountPerEventLimit,
        attributeCountPerLinkLimit = spanLimitsConfigImpl.attributeCountPerLinkLimit
    )

    private fun generateResource(): Resource = ResourceImpl(
        schemaUrl = schemaUrl,
        attributes = resourceAttrs.attributes
    )
}
