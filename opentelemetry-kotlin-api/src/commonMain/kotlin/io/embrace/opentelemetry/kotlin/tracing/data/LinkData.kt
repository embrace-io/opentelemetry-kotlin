package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * Immutable representation of a Link
 */
@ExperimentalApi
public interface LinkData {
    /**
     * The span context of the link.
     */
    public val spanContext: SpanContext

    /**
     * The attributes associated with the link.
     */
    public val attributes: Map<String, Any>
}
