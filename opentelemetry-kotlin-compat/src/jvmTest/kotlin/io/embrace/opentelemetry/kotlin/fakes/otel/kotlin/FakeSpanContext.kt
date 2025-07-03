@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

internal class FakeSpanContext(
    override val traceId: String = "traceId",
    override val spanId: String = "spanId",
    override val traceFlags: TraceFlags = FakeTraceFlags(),
    override val isValid: Boolean = true,
    override val isRemote: Boolean = false,
    override val traceState: TraceState = FakeTraceState(),
) : SpanContext
