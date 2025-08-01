package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent

@OptIn(ExperimentalApi::class)
public class SpanEventImpl(
    override val name: String,
    override val timestamp: Long,
    private val attributesContainer: AttributeContainer
) : SpanEvent, AttributeContainer by attributesContainer {
    override val attributes: Map<String, Any>
        get() = attributesContainer.attributes()
}
