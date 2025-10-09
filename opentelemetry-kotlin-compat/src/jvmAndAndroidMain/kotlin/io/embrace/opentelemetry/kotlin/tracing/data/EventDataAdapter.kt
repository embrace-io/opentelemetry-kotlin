package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.attributes.toMap

@OptIn(ExperimentalApi::class)
internal class EventDataAdapter(
    impl: OtelJavaEventData,
) : EventData {
    override val name: String = impl.name
    override val timestamp: Long = impl.epochNanos
    override val attributes: Map<String, Any> = impl.attributes.toMap()
}
