package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class FakeLinkData : LinkData {
    override val spanContext: SpanContext
        get() = TODO("Not yet implemented")
    override val attributes: Map<String, Any>
        get() = TODO("Not yet implemented")
}
