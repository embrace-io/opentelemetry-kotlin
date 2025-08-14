@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

class FakeSpanContext(
    override val traceId: String = "0".repeat(32),
    override val spanId: String = "0".repeat(16),
    override val traceFlags: TraceFlags = FakeTraceFlags(),
    override val traceState: TraceState = FakeTraceState(),
    override val isValid: Boolean = true,
    override val isRemote: Boolean = false,
) : SpanContext
