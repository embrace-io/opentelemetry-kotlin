package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaImmutableSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceFlags
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.context.ContextKey
import io.embrace.opentelemetry.kotlin.k2j.context.ContextKeyAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.TraceFlags
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
public fun SpanContext.toOtelJava(): OtelJavaSpanContext {
    return OtelJavaImmutableSpanContext.create(
        traceId,
        spanId,
        traceFlags.toOtelJava(),
        traceState.toOtelJava(),
        isRemote,
        false
    )
}

@OptIn(ExperimentalApi::class)
public fun OtelJavaSpanContext.toOtelKotlin(): SpanContext = SpanContextAdapter(this)

@OptIn(ExperimentalApi::class)
public fun <T> ContextKey<T>.toOtelJava(): OtelJavaContextKey<T> = (this as ContextKeyAdapter).impl

@OptIn(ExperimentalApi::class)
internal fun TraceFlags.toOtelJava(): OtelJavaTraceFlags {
    val sb = StringBuilder()
    sb.append(if (isRandom) "1" else "0")
    sb.append(if (isSampled) "1" else "0")
    return OtelJavaTraceFlags.fromHex(sb.toString(), 0)
}

@OptIn(ExperimentalApi::class)
internal fun TraceState.toOtelJava(): OtelJavaTraceState {
    return OtelJavaTraceState.builder().apply {
        asMap().entries.forEach {
            put(it.key, it.value)
        }
    }.build()
}
