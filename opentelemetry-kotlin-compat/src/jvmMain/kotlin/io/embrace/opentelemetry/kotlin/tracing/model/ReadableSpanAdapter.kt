package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.resource.ResourceAdapter
import io.embrace.opentelemetry.kotlin.scope.toOtelKotlinInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.EventDataAdapter
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkDataAdapter
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanDataAdapter
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelKotlinSpanKind
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelKotlinStatusData

@OptIn(ExperimentalApi::class)
internal class ReadableSpanAdapter(
    private val impl: OtelJavaReadableSpan
) : ReadableSpan {
    override val parent: SpanContext
    override val spanContext: SpanContext
    override val spanKind: SpanKind
    override val startTimestamp: Long
    override val resource: Resource
    override val instrumentationScopeInfo: InstrumentationScopeInfo

    override val name: String
        get() = impl.name
    override val status: StatusData
        get() = impl.toSpanData().status.toOtelKotlinStatusData()
    override val endTimestamp: Long
        get() = impl.toSpanData().endEpochNanos
    override val attributes: Map<String, Any>
        get() = impl.attributes.toMap()
    override val events: List<EventData>
        get() = impl.toSpanData().events.map(::EventDataAdapter)
    override val links: List<LinkData>
        get() = impl.toSpanData().links.map(::LinkDataAdapter)
    override val hasEnded: Boolean
        get() = impl.hasEnded()

    override fun toSpanData(): SpanData = SpanDataAdapter(impl.toSpanData())

    init {
        val initialState = impl.toSpanData()
        spanContext = SpanContextAdapter(impl.spanContext)
        parent = SpanContextAdapter(impl.parentSpanContext)
        spanKind = impl.kind.toOtelKotlinSpanKind()
        startTimestamp = initialState.startEpochNanos
        resource = ResourceAdapter(initialState.resource)
        instrumentationScopeInfo = impl.instrumentationScopeInfo.toOtelKotlinInstrumentationScopeInfo()
    }
}
