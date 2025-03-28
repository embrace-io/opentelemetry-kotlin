package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.Link
import io.embrace.opentelemetry.kotlin.tracing.SpanContext

@OptIn(ExperimentalApi::class)
internal class LinkImpl(
    override val spanContext: SpanContext,
    private val attributes: AttributeContainer
) : Link, AttributeContainer by attributes
