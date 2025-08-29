package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.kotlin.tracing.model.TraceFlags
import io.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
class SpanContextImpl(
    override val traceId: String,
    override val spanId: String,
    override val traceFlags: TraceFlags,
    override val isValid: Boolean,
    override val isRemote: Boolean,
    override val traceState: TraceState,
) : SpanContext
