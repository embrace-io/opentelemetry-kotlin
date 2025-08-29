package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * A read-only representation of a Link
 */
@ExperimentalApi
public interface LinkData : AttributeContainer {

    /**
     * The span context of the link.
     */
    @ThreadSafe
    public val spanContext: SpanContext
}
