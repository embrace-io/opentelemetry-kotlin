package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent

/**
 * Immutable representation of a SpanEvent
 */
@ExperimentalApi
public interface EventData : ReadableSpanEvent
