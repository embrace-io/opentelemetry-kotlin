package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.TraceState

@OptIn(ExperimentalApi::class)
class FakeTraceState(
    private val attrs: Map<String, String> = mapOf("foo" to "bar")
) : TraceState {
    override fun get(key: String): String? = attrs[key]

    override fun asMap(): Map<String, String> = attrs.toMap()
}
