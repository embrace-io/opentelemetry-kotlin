package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.TracingDsl

/**
 * Represents an event that happened on a span
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#add-events
 */
@TracingDsl
@ExperimentalApi
@ThreadSafe
public interface SpanEvent : AttributeContainer {

    /**
     * The name of the event
     */
    @ThreadSafe
    public val name: String

    /**
     * The timestamp of the event in nanoseconds
     */
    @ThreadSafe
    public val timestamp: Long
}
