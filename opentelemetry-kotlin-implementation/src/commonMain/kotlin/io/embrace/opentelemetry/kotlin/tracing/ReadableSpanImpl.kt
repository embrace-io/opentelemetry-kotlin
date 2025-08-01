package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
public class ReadableSpanImpl(
    override val name: String,
    override val status: StatusData,
    override val parent: SpanContext,
    override val spanContext: SpanContext,
    override val spanKind: SpanKind,
    override val startTimestamp: Long,
    override val endTimestamp: Long?,
    override val resource: Resource,
    override val instrumentationScopeInfo: InstrumentationScopeInfo,
    override val attributes: Map<String, Any>,
    override val events: List<EventData>,
    override val links: List<LinkData>,
    ended: () -> Boolean,
) : ReadableSpan {

    override val hasEnded: Boolean = ended()

    override fun toSpanData(): SpanData = SpanDataImpl(
        name = name,
        status = status,
        parent = parent,
        spanContext = spanContext,
        spanKind = spanKind,
        startTimestamp = startTimestamp,
        endTimestamp = endTimestamp,
        attributes = attributes,
        events = events,
        links = links,
        resource = resource,
        instrumentationScopeInfo = instrumentationScopeInfo,
        hasEnded = hasEnded
    )
}
