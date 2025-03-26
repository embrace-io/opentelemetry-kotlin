@file:Suppress("DEPRECATION")

package io.embrace.opentelemetry.kotlin.k2j.framework.serialization.fakes

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.data.EventData
import io.opentelemetry.sdk.trace.data.LinkData
import io.opentelemetry.sdk.trace.data.SpanData
import io.opentelemetry.sdk.trace.data.StatusData

internal class FakeSpanData(
    val implName: String = "fake_span",
    val implSpanKind: SpanKind = SpanKind.INTERNAL,
    val implSpanContext: SpanContext = SpanContext.getInvalid(),
    val implParentSpanContext: SpanContext = SpanContext.getInvalid(),
    val implAttributes: Attributes = Attributes.of(AttributeKey.stringKey("key"), "value"),
    val implEventData: MutableList<EventData> = mutableListOf(EventData.create(150, "event", implAttributes)),
    val implLinkData: MutableList<LinkData> = mutableListOf(LinkData.create(SpanContext.getInvalid(), implAttributes)),
    val implStartNs: Long = 100,
    val implEndNs: Long = implStartNs + 100,
    val implEnded: Boolean = true,
    val implStatusData: StatusData = StatusData.create(StatusCode.ERROR, "Whoops"),
    val implResource: Resource = Resource.create(implAttributes, "http://example.com/foo"),
) : SpanData {

    override fun getName(): String = implName
    override fun getKind(): SpanKind = implSpanKind
    override fun getSpanContext(): SpanContext = implSpanContext
    override fun getParentSpanContext(): SpanContext = implParentSpanContext
    override fun getStatus(): StatusData = implStatusData
    override fun getStartEpochNanos(): Long = implStartNs
    override fun getAttributes(): Attributes = implAttributes
    override fun getEvents(): MutableList<EventData> = implEventData
    override fun getLinks(): MutableList<LinkData> = implLinkData
    override fun getEndEpochNanos(): Long = implEndNs
    override fun hasEnded(): Boolean = implEnded
    override fun getTotalRecordedEvents(): Int = events.size
    override fun getTotalRecordedLinks(): Int = links.size
    override fun getTotalAttributeCount(): Int = implAttributes.size()

    @Deprecated("Deprecated in Java")
    override fun getInstrumentationLibraryInfo(): InstrumentationLibraryInfo = InstrumentationLibraryInfo.empty()
    override fun getResource(): Resource = implResource
}
