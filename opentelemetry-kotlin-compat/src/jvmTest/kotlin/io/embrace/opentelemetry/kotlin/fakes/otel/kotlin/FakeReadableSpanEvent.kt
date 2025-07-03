package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent

@OptIn(ExperimentalApi::class)
internal class FakeReadableSpanEvent(
    override val name: String = "event",
    override val timestamp: Long = 1000,
    override val attributes: Map<String, Any> = mapOf("key" to "value")
) : ReadableSpanEvent
