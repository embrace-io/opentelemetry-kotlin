package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class FakeSpanData : SpanData {
    override val name: String
        get() = TODO("Not yet implemented")
    override val status: StatusData
        get() = TODO("Not yet implemented")
    override val parent: SpanContext
        get() = TODO("Not yet implemented")
    override val spanContext: SpanContext
        get() = TODO("Not yet implemented")
    override val spanKind: SpanKind
        get() = TODO("Not yet implemented")
    override val startTimestamp: Long
        get() = TODO("Not yet implemented")
    override val endTimestamp: Long?
        get() = TODO("Not yet implemented")
    override val attributes: Map<String, Any>
        get() = TODO("Not yet implemented")
    override val events: List<EventData>
        get() = TODO("Not yet implemented")
    override val links: List<LinkData>
        get() = TODO("Not yet implemented")
    override val resource: Resource
        get() = TODO("Not yet implemented")
    override val instrumentationScopeInfo: InstrumentationScopeInfo
        get() = TODO("Not yet implemented")
}
