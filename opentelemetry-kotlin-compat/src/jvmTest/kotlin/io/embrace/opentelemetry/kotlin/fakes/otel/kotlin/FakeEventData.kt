package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.EventData

@OptIn(ExperimentalApi::class)
internal class FakeEventData(
    override val name: String = "event",
    override val timestamp: Long = 1000,
    override val attributes: Map<String, Any> = mapOf("key" to "value")
) : EventData
