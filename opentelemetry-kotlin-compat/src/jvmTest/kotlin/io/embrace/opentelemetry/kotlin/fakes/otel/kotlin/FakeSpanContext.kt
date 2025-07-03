@file:OptIn(ExperimentalApi::class)

package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState
import io.opentelemetry.sdk.trace.IdGenerator

internal class FakeSpanContext(
    private val idGenerator: IdGenerator = IdGenerator.random(),
    override val traceId: String = idGenerator.generateTraceId(),
    override val spanId: String = idGenerator.generateSpanId(),
    override val traceFlags: TraceFlags = FakeTraceFlags(),
    override val isValid: Boolean = true,
    override val isRemote: Boolean = false,
    override val traceState: TraceState = FakeTraceState(),
) : SpanContext
