package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * A writable representation of a [Span] that can be modified.
 */
@ExperimentalApi
public interface ReadWriteSpan : Span, ReadableSpan
