package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTraceState

@OptIn(ExperimentalApi::class)
internal class TraceStateAdapter(
    private val traceState: OtelJavaTraceState
) : TraceState {

    override fun get(key: String): String? = traceState.get(key)
    override fun asMap(): Map<String, String> = traceState.asMap()
}
