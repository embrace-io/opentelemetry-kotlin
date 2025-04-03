package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@OptIn(ExperimentalApi::class)
class SpanEventImpl(
    override val name: String,
    override val timestamp: Long,
    private val attributes: AttributeContainer
) : SpanEvent, AttributeContainer by attributes
