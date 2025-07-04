package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent

@OptIn(ExperimentalApi::class)
internal class ReadableSpanEventAdapter(
    impl: OtelJavaEventData
) : ReadableSpanEvent {
    override val name: String = impl.name
    override val timestamp: Long = impl.epochNanos
    override val attributes: Map<String, Any> = impl.attributes.toMap()
}
