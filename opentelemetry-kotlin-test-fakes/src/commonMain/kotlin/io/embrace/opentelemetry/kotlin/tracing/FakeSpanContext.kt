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
    override val isRemote: Boolean = false,
) : SpanContext {

    companion object {
        val INVALID = FakeSpanContext()
        val VALID = FakeSpanContext(
            traceId = "12345678901234567890123456789012",
            spanId = "1234567890123456",
        )
    }

    override val isValid: Boolean = traceId != "0".repeat(32) && spanId != "0".repeat(16)
}
