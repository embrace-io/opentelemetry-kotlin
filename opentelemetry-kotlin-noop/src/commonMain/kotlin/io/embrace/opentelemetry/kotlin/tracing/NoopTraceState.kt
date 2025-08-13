package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@ExperimentalApi
internal object NoopTraceState : TraceState {
    override fun get(key: String): String? = null
    override fun asMap(): Map<String, String> = emptyMap()
    override fun put(key: String, value: String): TraceState = this
    override fun remove(key: String): TraceState = this
}
