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
import io.embrace.opentelemetry.kotlin.k2j.tracing.convertToOtelJava
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData

@OptIn(ExperimentalApi::class)
internal fun SpanData.toOtelJavaSpanData(): OtelJavaSpanData {
    return OtelJavaSpanDataImpl(
        nameImpl = name,
        statusImpl = OtelJavaStatusData.create(status.statusCode.convertToOtelJava(), status.description),
        parentSpanContextImpl = parent.convertToOtelJava(),
        spanContextImpl = spanContext.convertToOtelJava(),
        kindImpl = spanKind.convertToOtelJava(),
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
                it.spanContext.convertToOtelJava(),
                attrsFromMap(it.attributes)
            )
        }.toMutableList(),
        hasEndedImpl = endTimestamp != null
    )
}
