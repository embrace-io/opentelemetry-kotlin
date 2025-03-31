package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.fakes.FakeSpanData
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.data.EventData
import io.opentelemetry.sdk.trace.data.LinkData
import io.opentelemetry.sdk.trace.data.StatusData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SerializableSpanDataTest {

    @Test
    fun `test conversion`() {
        val fake = FakeSpanData()
        val observed = fake.toSerializable()

        assertEquals(fake.implName, observed.name)
        assertEquals(fake.kind.name, observed.kind)
        compareSpanContexts(fake.spanContext, observed.spanContext)
        compareSpanContexts(fake.parentSpanContext, observed.parentSpanContext)
        compareStatusData(fake.status, observed.statusData)
        assertEquals(fake.startEpochNanos, observed.startTimestampNs)
        compareAttributes(fake.attributes, observed.attributes)
        compareEvents(fake.events, observed.events)
        compareLinks(fake.links, observed.links)
        assertEquals(fake.endEpochNanos, observed.endTimestampNs)
        assertTrue(fake.hasEnded())
        assertEquals(fake.totalRecordedEvents, observed.totalRecordedEvents)
        assertEquals(fake.totalRecordedLinks, observed.totalRecordedLinks)
        assertEquals(fake.totalAttributeCount, observed.totalAttributeCount)
        compareResource(fake.resource, observed.resource)
    }

    private fun compareSpanContexts(expected: SpanContext, observed: SerializableSpanContext) {
        assertEquals(expected.traceId, observed.traceId)
        assertEquals(expected.spanId, observed.spanId)
        assertEquals(expected.traceState.asMap(), observed.traceState)
        assertEquals(expected.traceFlags.asHex(), observed.traceFlags)
    }

    private fun compareStatusData(expected: StatusData, observed: SerializableSpanStatusData) {
        assertEquals(expected.statusCode.ordinal, observed.code)
        assertEquals(expected.description, observed.description)
    }

    private fun compareAttributes(expected: Attributes, observed: Map<String, String>) {
        assertEquals(expected.toMap(), observed)
    }

    private fun compareEvents(expected: List<EventData>, observed: List<SerializableEventData>) {
        assertEquals(expected.size, observed.size)

        expected.forEachIndexed { index, data ->
            val observedData = observed[index]
            assertEquals(data.name, observedData.name)
            assertEquals(data.epochNanos, observedData.timestampNs)
            compareAttributes(data.attributes, observedData.attributes)
        }
    }

    private fun compareLinks(expected: List<LinkData>, observed: List<SerializableLinkData>) {
        assertEquals(expected.size, observed.size)

        expected.forEachIndexed { index, data ->
            val observedData = observed[index]
            compareSpanContexts(data.spanContext, observedData.spanContext)
            compareAttributes(data.attributes, observedData.attributes)
        }
    }

    private fun compareResource(expected: Resource, observed: SerializableResource) {
        assertEquals(expected.schemaUrl, observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun Attributes.toMap(): Map<String, Any> {
        return this.asMap().mapKeys { it.key.key }
    }
}
