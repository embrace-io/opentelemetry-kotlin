package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.k2j.OtelJavaImmutableSpanContext
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.k2j.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.TraceState

internal fun SpanContext.convertToOtelJava(): OtelJavaSpanContext {
    return OtelJavaImmutableSpanContext.create(
        traceId,
        spanId,
        traceFlags.convertToOtelJava(),
        getTraceState().convertToOtelJava(),
        isRemote,
        false
    )
}

internal fun TraceFlags.convertToOtelJava(): OtelJavaTraceFlags {
    val sb = StringBuilder()
    sb.append(if (isRandom) "1" else "0")
    sb.append(if (isSampled) "1" else "0")
    return OtelJavaTraceFlags.fromHex(sb.toString(), 0)
}

internal fun TraceState.convertToOtelJava(): OtelJavaTraceState {
    return OtelJavaTraceState.builder().apply {
        asMap().entries.forEach {
            put(it.key, it.value)
        }
    }.build()
}
