package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.TraceState
import io.opentelemetry.api.internal.ImmutableSpanContext

internal fun SpanContext.convertToOtelJava(): io.opentelemetry.api.trace.SpanContext {
    return ImmutableSpanContext.create(
        traceId,
        spanId,
        traceFlags.convertToOtelJava(),
        getTraceState().convertToOtelJava(),
        isRemote,
        false
    )
}

internal fun TraceFlags.convertToOtelJava(): io.opentelemetry.api.trace.TraceFlags {
    val sb = StringBuilder()
    sb.append(if (isRandom) "1" else "0")
    sb.append(if (isSampled) "1" else "0")
    return io.opentelemetry.api.trace.TraceFlags.fromHex(sb.toString(), 0)
}

internal fun TraceState.convertToOtelJava(): io.opentelemetry.api.trace.TraceState {
    return io.opentelemetry.api.trace.TraceState.builder().apply {
        asMap().entries.forEach {
            put(it.key, it.value)
        }
    }.build()
}
