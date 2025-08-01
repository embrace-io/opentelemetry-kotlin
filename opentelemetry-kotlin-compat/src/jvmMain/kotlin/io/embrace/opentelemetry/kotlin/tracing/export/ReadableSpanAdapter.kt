package io.embrace.opentelemetry.kotlin.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.resource.ResourceAdapter
import io.embrace.opentelemetry.kotlin.scope.toOtelKotlinInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanDataAdapter
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelKotlinSpanKind
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelKotlinStatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class ReadableSpanAdapter(
    private val impl: OtelJavaReadableSpan
) : ReadableSpan {
    override val name: String = impl.name
    override val status: StatusData
        get() = getSnapshot().status.toOtelKotlinStatusData()
    override val parent: SpanContext = SpanContextAdapter(impl.parentSpanContext)
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val spanKind: SpanKind = impl.kind.toOtelKotlinSpanKind()
    override val startTimestamp: Long
        get() = getSnapshot().startEpochNanos
    override val endTimestamp: Long
        get() = getSnapshot().endEpochNanos
    override val resource: Resource = ResourceAdapter(getSnapshot().resource)
    override val instrumentationScopeInfo: InstrumentationScopeInfo =
        impl.instrumentationScopeInfo.toOtelKotlinInstrumentationScopeInfo()
    override val attributes: Map<String, Any> = impl.attributes.toMap()
    override val events: List<EventData>
        get() = getSnapshot().events.map(::EventDataAdapter)
    override val links: List<LinkData>
        get() = getSnapshot().links.map(::LinkDataAdapter)
    override val hasEnded: Boolean = impl.hasEnded()

    override fun toSpanData(): SpanData = SpanDataAdapter(getSnapshot())

    private fun getSnapshot() = impl.toSpanData()
}
