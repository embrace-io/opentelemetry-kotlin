package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
internal class TraceStateAdapter(
    private val traceState: OtelJavaTraceState
) : TraceState {

    override fun get(key: String): String? = traceState.get(key)
    override fun asMap(): Map<String, String> = traceState.asMap()
}
