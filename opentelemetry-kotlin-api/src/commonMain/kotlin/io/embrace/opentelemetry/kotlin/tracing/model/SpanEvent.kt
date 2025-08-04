package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.TracingDsl
import io.embrace.opentelemetry.kotlin.tracing.data.EventData

/**
 * Represents an event that happened on a span
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#add-events
 */
@TracingDsl
@ExperimentalApi
@ThreadSafe
public interface SpanEvent : EventData, MutableAttributeContainer
