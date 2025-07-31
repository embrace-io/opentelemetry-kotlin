package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal fun TraceState.toOtelJava(): OtelJavaTraceState {
    return OtelJavaTraceState.builder().apply {
        asMap().entries.forEach {
            put(it.key, it.value)
        }
    }.build()
}
