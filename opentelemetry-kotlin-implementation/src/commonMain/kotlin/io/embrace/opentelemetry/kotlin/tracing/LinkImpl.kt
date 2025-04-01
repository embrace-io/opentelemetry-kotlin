package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@OptIn(ExperimentalApi::class)
class LinkImpl(
    override val spanContext: SpanContext,
    private val attributes: AttributeContainer
) : Link, AttributeContainer by attributes
