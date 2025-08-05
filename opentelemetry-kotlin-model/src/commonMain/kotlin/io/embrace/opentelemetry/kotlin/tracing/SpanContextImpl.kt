package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
class SpanContextImpl(
    override val traceId: String,
    override val spanId: String,
    override val traceFlags: TraceFlags,
    override val isValid: Boolean,
    override val isRemote: Boolean,
    override val traceState: TraceState,
) : SpanContext
