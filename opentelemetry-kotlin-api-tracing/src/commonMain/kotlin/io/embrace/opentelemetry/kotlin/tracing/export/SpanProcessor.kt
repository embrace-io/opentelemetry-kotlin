package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.TelemetryCloseable
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan

/**
 * Processes spans before they are exported as batches.
 */
@ExperimentalApi
public interface SpanProcessor : TelemetryCloseable {

    /**
     * Invoked when a span is created.
     *
     * @param span A reference to the span that has been created. This reference can be held & used to update the span.
     * @param parentContext The context of the parent span or the current context if there is none.
     */
    public fun onStart(span: ReadWriteSpan, parentContext: Context)

    /**
     * Invoked after a span has ended with an immutable representation of the span.
     */
    public fun onEnd(span: ReadableSpan)

    /**
     * Determines whether this span processor is required when a span starts.
     */
    public fun isStartRequired(): Boolean

    /**
     * Determines whether this span processor is required when a span ends.
     */
    public fun isEndRequired(): Boolean
}
