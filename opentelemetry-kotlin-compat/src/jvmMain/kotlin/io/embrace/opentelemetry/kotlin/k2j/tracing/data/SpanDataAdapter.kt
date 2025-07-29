package io.embrace.opentelemetry.kotlin.k2j.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.j2k.bridge.ResourceAdapter
import io.embrace.opentelemetry.kotlin.j2k.bridge.toOtelKotlin
import io.embrace.opentelemetry.kotlin.j2k.tracing.toOtelKotlin
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.k2j.tracing.toOtelKotlin
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class SpanDataAdapter(
    val impl: OtelJavaSpanData,
) : SpanData {
    override val name: String = impl.name
    override val status: StatusData = impl.status.toOtelKotlin()
    override val parent: SpanContext = SpanContextAdapter(impl.parentSpanContext)
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val spanKind: SpanKind = impl.kind.toOtelKotlin()
    override val startTimestamp: Long = impl.startEpochNanos
    override val endTimestamp: Long? = impl.endEpochNanos
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val events: List<EventData> = impl.events.map { EventDataAdapter(it) }
    override val links: List<LinkData> = impl.links.map { LinkDataAdapter(it) }
    override val resource: Resource = ResourceAdapter(impl.resource)
    override val instrumentationScopeInfo: InstrumentationScopeInfo = impl.instrumentationScopeInfo.toOtelKotlin()
}
