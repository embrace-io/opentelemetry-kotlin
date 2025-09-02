package io.embrace.opentelemetry.kotlin.factory

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.SpanContextImpl
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class SpanContextFactoryImpl(
    private val traceFlagsFactory: TraceFlagsFactory = TraceFlagsFactoryImpl(),
    private val traceStateFactory: TraceStateFactory = TraceStateFactoryImpl()
) : SpanContextFactory {

    override val invalid: SpanContext by lazy {
        SpanContextImpl(
            traceId = "00000000000000000000000000000000",
            spanId = "0000000000000000",
            traceFlags = traceFlagsFactory.default,
            isValid = false,
            isRemote = false,
            traceState = traceStateFactory.default
        )
    }

    override fun create(
        traceId: String,
        spanId: String,
        traceFlags: TraceFlags,
        traceState: TraceState
    ): SpanContext {
        val isValidTraceId = isValidTraceId(traceId)
        val isValidSpanId = isValidSpanId(spanId)

        return SpanContextImpl(
            traceId = when {
                isValidTraceId -> traceId
                else -> "00000000000000000000000000000000"
            },
            spanId = when {
                isValidSpanId -> spanId
                else -> "0000000000000000"
            },
            traceFlags = traceFlags,
            isValid = isValidTraceId && isValidSpanId,
            isRemote = false,
            traceState = traceState
        )
    }

    private fun isValidTraceId(traceId: String): Boolean {
        // Must be 32 hex characters (16 bytes)
        if (traceId.length != 32) {
            return false
        }

        // Must be valid hex
        if (!traceId.isValidHex()) {
            return false
        }

        // Must have at least one non-zero byte (not all zeros)
        return traceId != "00000000000000000000000000000000"
    }

    private fun isValidSpanId(spanId: String): Boolean {
        // Must be 16 hex characters (8 bytes)
        if (spanId.length != 16) {
            return false
        }

        // Must be valid hex
        if (!spanId.isValidHex()) {
            return false
        }

        // Must have at least one non-zero byte (not all zeros)
        return spanId != "0000000000000000"
    }
}
