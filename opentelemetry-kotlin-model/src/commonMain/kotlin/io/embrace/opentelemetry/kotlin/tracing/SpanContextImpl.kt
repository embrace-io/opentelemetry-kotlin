package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.factory.toHexString
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
class SpanContextImpl(
    override val traceIdBytes: ByteArray,
    override val spanIdBytes: ByteArray,
    override val traceFlags: TraceFlags,
    override val isValid: Boolean,
    override val isRemote: Boolean,
    override val traceState: TraceState,
) : SpanContext {

    override val traceId: String by lazy {
        traceIdBytes.toHexString()
    }
    override val spanId: String by lazy {
        spanIdBytes.toHexString()
    }
}
