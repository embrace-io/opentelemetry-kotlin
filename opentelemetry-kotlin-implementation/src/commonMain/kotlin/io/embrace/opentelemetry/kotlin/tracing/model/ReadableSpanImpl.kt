package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * A view of [SpanRecord] that is returned when only read operations are permissible on a span.
 * Currently this is just in [io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor].
 */
@OptIn(ExperimentalApi::class)
internal class ReadableSpanImpl(private val record: SpanRecord) : ReadWriteSpan by record
