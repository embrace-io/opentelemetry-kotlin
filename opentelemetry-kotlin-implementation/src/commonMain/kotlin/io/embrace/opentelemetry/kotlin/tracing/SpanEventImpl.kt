package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent

@OptIn(ExperimentalApi::class)
public class SpanEventImpl(
    override val name: String,
    override val timestamp: Long,
    private val attributesContainer: MutableAttributeContainer
) : SpanEvent, MutableAttributeContainer by attributesContainer
