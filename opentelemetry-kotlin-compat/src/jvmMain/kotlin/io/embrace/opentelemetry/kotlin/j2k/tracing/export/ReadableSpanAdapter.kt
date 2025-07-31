package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.j2k.bridge.ResourceAdapter
import io.embrace.opentelemetry.kotlin.j2k.bridge.toOtelKotlin
import io.embrace.opentelemetry.kotlin.j2k.tracing.toOtelKotlin
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.data.EventDataAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.data.LinkDataAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.data.SpanDataAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class ReadableSpanAdapter(
    private val impl: OtelJavaReadableSpan
) : ReadableSpan {
    override val name: String = impl.name
    override val status: StatusData = impl.toSpanData().status.toOtelKotlin()
    override val parent: SpanContext = SpanContextAdapter(impl.parentSpanContext)
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val spanKind: SpanKind = impl.kind.toOtelKotlin()
    override val startTimestamp: Long = impl.toSpanData().startEpochNanos
    override val endTimestamp: Long = impl.toSpanData().endEpochNanos
    override val resource: Resource = ResourceAdapter(impl.toSpanData().resource)
    override val instrumentationScopeInfo: InstrumentationScopeInfo = impl.instrumentationScopeInfo.toOtelKotlin()
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val events: List<EventData> = impl.toSpanData().events.map(::EventDataAdapter)
    override val links: List<LinkData> = impl.toSpanData().links.map(::LinkDataAdapter)
    override val hasEnded: Boolean = impl.hasEnded()

    override fun toSpanData(): SpanData = SpanDataAdapter(impl.toSpanData())
}
