package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.model.Link
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
public class LinkImpl(
    override val spanContext: SpanContext,
    private val attributesContainer: AttributeContainer
) : Link, AttributeContainer by attributesContainer {
    override val attributes: Map<String, Any>
        get() = attributesContainer.attributes()
}
