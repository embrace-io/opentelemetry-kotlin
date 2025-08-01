@file:Suppress("DEPRECATION", "TYPEALIAS_EXPANSION_DEPRECATION")

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationLibraryInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.attributes.attrsFromMap
import io.embrace.opentelemetry.kotlin.attributes.resourceFromMap
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaStatusCode
import io.embrace.opentelemetry.kotlin.tracing.model.OtelJavaSpanDataImpl

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
