package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.resource.ResourceAdapter
import io.embrace.opentelemetry.kotlin.scope.toOtelKotlinInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelKotlinSpanKind
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelKotlinStatusData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class SpanDataAdapter(
    val impl: OtelJavaSpanData,
) : SpanData {
    override val name: String = impl.name
    override val status: StatusData = impl.status.toOtelKotlinStatusData()
    override val parent: SpanContext = SpanContextAdapter(impl.parentSpanContext)
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val spanKind: SpanKind = impl.kind.toOtelKotlinSpanKind()
    override val startTimestamp: Long = impl.startEpochNanos
    override val endTimestamp: Long? = impl.endEpochNanos
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val events: List<EventData> = impl.events.map { EventDataAdapter(it) }
    override val links: List<LinkData> = impl.links.map { LinkDataAdapter(it) }
    override val resource: Resource = ResourceAdapter(impl.resource)
    override val instrumentationScopeInfo: InstrumentationScopeInfo = impl.instrumentationScopeInfo.toOtelKotlinInstrumentationScopeInfo()
    override val hasEnded: Boolean = impl.hasEnded()
}
