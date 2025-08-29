package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.ThreadSafe
import io.opentelemetry.kotlin.tracing.model.SpanContext

/**
 * Gets the trace ID as a byte array.
 */
@ExperimentalApi
@ThreadSafe
public val SpanContext.traceIdBytes: ByteArray get() = traceId.encodeToByteArray()

/**
 * Gets the span ID as a byte array.
 */
@ExperimentalApi
@ThreadSafe
public val SpanContext.spanIdBytes: ByteArray get() = spanId.encodeToByteArray()
