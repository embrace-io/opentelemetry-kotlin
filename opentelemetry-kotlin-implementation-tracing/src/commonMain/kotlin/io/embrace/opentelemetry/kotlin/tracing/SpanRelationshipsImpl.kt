package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.init.config.SpanLimitConfig
import io.embrace.opentelemetry.kotlin.threadSafeList
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanRelationships

@OptIn(ExperimentalApi::class)
internal class SpanRelationshipsImpl(
    val clock: Clock,
    val spanLimitConfig: SpanLimitConfig,
    val attrs: MutableAttributeContainer = MutableAttributeContainerImpl(spanLimitConfig.attributeCountLimit),
) : SpanRelationships, MutableAttributeContainer by attrs {

    val links = threadSafeList<LinkData>()
    val events = threadSafeList<EventData>()

    override fun addLink(
        spanContext: SpanContext,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        if (links.size < spanLimitConfig.linkCountLimit) {
            val attrs = MutableAttributeContainerImpl(spanLimitConfig.attributeCountPerLinkLimit).apply(attributes)
            links.add(LinkImpl(spanContext, attrs))
        }
    }

    override fun addEvent(
        name: String,
        timestamp: Long?,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        if (events.size < spanLimitConfig.eventCountLimit) {
            val attrs = MutableAttributeContainerImpl(spanLimitConfig.attributeCountPerEventLimit).apply(attributes)
            events.add(SpanEventImpl(name, timestamp ?: clock.now(), attrs))
        }
    }
}
