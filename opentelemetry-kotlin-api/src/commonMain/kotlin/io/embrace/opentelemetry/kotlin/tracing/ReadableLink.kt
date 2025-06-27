package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * A read-only representation of a span link.
 */
@ExperimentalApi
public interface ReadableLink {

    /**
     * The span context of the link.
     */
    public val spanContext: SpanContext

    /**
     * The attributes associated with the link.
     */
    public val attributes: Map<String, Any>
}
