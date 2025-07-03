@file:Suppress("TYPEALIAS_EXPANSION_DEPRECATION", "DEPRECATION")

package io.embrace.opentelemetry.kotlin.j2k.bridge

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaEventData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationLibraryInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.data.EventData
import io.opentelemetry.sdk.trace.data.LinkData
import io.opentelemetry.sdk.trace.data.StatusData

/**
 * Implementation of [OtelJavaSpanData] that we can construct new instances of. Required for
 * backwards compatibility with opentelemetry-java exporters.
 */
@Suppress("DEPRECATION")
internal class OtelJavaSpanDataImpl(
    private val nameImpl: String,
    private val kindImpl: OtelJavaSpanKind,
    private val spanContextImpl: OtelJavaSpanContext,
    private val parentSpanContextImpl: OtelJavaSpanContext,
    private val statusImpl: OtelJavaStatusData,
    private val startEpochNanosImpl: Long,
    private val attributesImpl: OtelJavaAttributes,
    private val eventsImpl: MutableList<OtelJavaEventData>,
    private val linksImpl: MutableList<OtelJavaLinkData>,
    private val endEpochNanosImpl: Long,
    private val hasEndedImpl: Boolean,
    private val scopeImpl: OtelJavaInstrumentationLibraryInfo,
    private val resourceImpl: OtelJavaResource,
) : OtelJavaSpanData {

    override fun getName(): String = nameImpl
    override fun getKind(): SpanKind = kindImpl
    override fun getSpanContext(): SpanContext = spanContextImpl
    override fun getParentSpanContext(): SpanContext = parentSpanContextImpl
    override fun getStatus(): StatusData = statusImpl
    override fun getStartEpochNanos(): Long = startEpochNanosImpl
    override fun getAttributes(): Attributes = attributesImpl
    override fun getEvents(): MutableList<EventData> = eventsImpl
    override fun getLinks(): MutableList<LinkData> = linksImpl
    override fun getEndEpochNanos(): Long = endEpochNanosImpl
    override fun hasEnded(): Boolean = hasEndedImpl
    override fun getTotalRecordedEvents(): Int = eventsImpl.size
    override fun getTotalRecordedLinks(): Int = linksImpl.size
    override fun getTotalAttributeCount(): Int = attributesImpl.size()

    @Deprecated("Deprecated in Java")
    override fun getInstrumentationLibraryInfo(): InstrumentationLibraryInfo = scopeImpl
    override fun getResource(): Resource = resourceImpl
}
