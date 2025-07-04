package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.j2k.bridge.ResourceAdapter
import io.embrace.opentelemetry.kotlin.j2k.bridge.convertToOtelKotlin
import io.embrace.opentelemetry.kotlin.j2k.tracing.convertToOtelKotlin
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableLink
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class ReadableSpanAdapter(
    private val impl: OtelJavaReadableSpan
) : ReadableSpan {

    private val data = impl.toSpanData()

    override val name: String = impl.name
    override val status: StatusCode = data.status.convertToOtelKotlin()
    override val parent: SpanContext = SpanContextAdapter(impl.parentSpanContext)
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val spanKind: SpanKind = impl.kind.convertToOtelKotlin()
    override val startTimestamp: Long = data.startEpochNanos
    override val endTimestamp: Long = data.endEpochNanos
    override val resource: Resource = ResourceAdapter(data.resource)
    override val instrumentationScopeInfo: InstrumentationScopeInfo =
        impl.instrumentationScopeInfo.convertToOtelKotlin()
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val events: List<ReadableSpanEvent> = data.events.map(::ReadableSpanEventAdapter)
    override val links: List<ReadableLink> = data.links.map(::ReadableLinkAdapter)
    override fun hasEnded(): Boolean = impl.hasEnded()
}
