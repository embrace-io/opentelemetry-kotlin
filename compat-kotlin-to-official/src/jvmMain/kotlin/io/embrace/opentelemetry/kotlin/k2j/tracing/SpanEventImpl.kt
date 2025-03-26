package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.SpanEvent

internal class SpanEventImpl(
    override val name: String,
    override val timestamp: Long,
    private val attributes: AttributeContainer
) : SpanEvent, AttributeContainer by attributes
