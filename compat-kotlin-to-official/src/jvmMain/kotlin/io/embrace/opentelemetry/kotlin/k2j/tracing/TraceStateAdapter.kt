package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.opentelemetry.api.trace.TraceState

internal class TraceStateAdapter(
    private val traceState: TraceState
) : io.embrace.opentelemetry.kotlin.tracing.TraceState {

    override fun get(key: String): String? = traceState.get(key)
    override fun asMap(): Map<String, String> = traceState.asMap()
}
