@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationLibraryInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.j2k.bridge.OtelJavaSpanDataImpl
import io.embrace.opentelemetry.kotlin.j2k.bridge.attrsFromMap
import io.embrace.opentelemetry.kotlin.j2k.bridge.resourceFromMap
import io.embrace.opentelemetry.kotlin.k2j.tracing.toOtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.k2j.tracing.toOtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.k2j.tracing.toOtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
internal fun SpanData.toOtelJavaSpanData(): OtelJavaSpanData {
    return OtelJavaSpanDataImpl(
        nameImpl = name,
        statusImpl = OtelJavaStatusData.create(status.statusCode.toOtelJavaStatusCode(), status.description),
        parentSpanContextImpl = parent.toOtelJavaSpanContext(),
        spanContextImpl = spanContext.toOtelJavaSpanContext(),
        kindImpl = spanKind.toOtelJavaSpanKind(),
        startEpochNanosImpl = startTimestamp,
        endEpochNanosImpl = endTimestamp ?: 0,
        resourceImpl = resource.let(::resourceFromMap),
        scopeImpl = OtelJavaInstrumentationLibraryInfo.create(
            instrumentationScopeInfo.name,
            instrumentationScopeInfo.version,
            instrumentationScopeInfo.schemaUrl
        ),
        attributesImpl = attrsFromMap(attributes),
        eventsImpl = events.map {
            OtelJavaEventData.create(
                it.timestamp,
                it.name,
                attrsFromMap(it.attributes)
            )
        }.toMutableList(),
        linksImpl = links.map {
            OtelJavaLinkData.create(
                it.spanContext.toOtelJavaSpanContext(),
                attrsFromMap(it.attributes)
            )
        }.toMutableList(),
        hasEndedImpl = hasEnded
    )
}
