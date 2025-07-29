package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

/**
 * A read-only representation of a span.
 */
@ExperimentalApi
public interface ReadableSpan : SpanData {
    /**
     * Return an immutable instance of the span at the time of invocation.
     */
    public fun toSpanData(): SpanData
}
