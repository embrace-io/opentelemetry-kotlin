package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Defines limits on how much data should be captured in a span.
 */
@ExperimentalApi
public interface SpanLimits {
    public val attributeCountLimit: Int
    public val linkCountLimit: Int
    public val eventCountLimit: Int
    public val attributeCountPerEventLimit: Int
    public val attributeCountPerLinkLimit: Int
}
