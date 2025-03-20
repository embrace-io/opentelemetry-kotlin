package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

/**
 * Represents a link to a [SpanContext] and optional attributes further describing the link.
 */
public interface Link : AttributeContainer {

    /**
     * The [SpanContext] of the linked span.
     */
    public val spanContext: SpanContext
}
