@file:Suppress("TYPEALIAS_EXPANSION_DEPRECATION")

package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationLibraryInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.j2k.bridge.OtelJavaSpanDataImpl
import io.embrace.opentelemetry.kotlin.j2k.bridge.attrsFromMap
import io.embrace.opentelemetry.kotlin.j2k.bridge.resourceFromMap
import io.embrace.opentelemetry.kotlin.k2j.tracing.convertToOtelJava
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.opentelemetry.sdk.trace.data.SpanData

@OptIn(ExperimentalApi::class)
internal fun ReadableSpan.toSpanData(): SpanData {
    return OtelJavaSpanDataImpl(
        nameImpl = name,
        statusImpl = OtelJavaStatusData.create(status.convertToOtelJava(), null),
        parentSpanContextImpl = parent?.convertToOtelJava() ?: OtelJavaSpanContext.getInvalid(),
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
        hasEndedImpl = hasEnded()
    )
}
