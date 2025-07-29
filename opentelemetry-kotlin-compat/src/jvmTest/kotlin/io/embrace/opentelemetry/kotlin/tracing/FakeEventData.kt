package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.EventData

@OptIn(ExperimentalApi::class)
internal class FakeEventData : EventData {
    override val name: String
        get() = TODO("Not yet implemented")
    override val timestamp: Long
        get() = TODO("Not yet implemented")
    override val attributes: Map<String, Any>
        get() = TODO("Not yet implemented")
}
