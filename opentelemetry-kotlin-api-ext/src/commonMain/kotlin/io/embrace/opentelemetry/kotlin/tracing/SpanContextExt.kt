package io.embrace.opentelemetry.kotlin.tracing

/**
 * Gets the trace ID as a byte array.
 */
public val SpanContext.traceIdBytes: ByteArray get() = traceId.toByteArray()

/**
 * Gets the span ID as a byte array.
 */
public val SpanContext.spanIdBytes: ByteArray get() = spanId.toByteArray()
