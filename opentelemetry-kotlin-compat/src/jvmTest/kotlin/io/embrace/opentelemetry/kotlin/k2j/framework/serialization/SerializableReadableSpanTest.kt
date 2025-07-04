package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeReadableSpan
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toStringMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableLink
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SerializableReadableSpanTest {

    @Test
    fun `test conversion`() {
        val fake = FakeReadableSpan()
        val observed = fake.toSerializable(true)

        assertEquals(fake.name, observed.name)
        assertEquals(fake.spanKind.name, observed.kind)
        compareSpanContexts(fake.spanContext, observed.spanContext)
        compareSpanContexts(checkNotNull(fake.parent), observed.parentSpanContext)
        assertEquals(fake.status.toSerializable().description, observed.statusData.description)
        assertEquals(fake.startTimestamp, observed.startTimestampNs)
        assertEquals(fake.endTimestamp, observed.endTimestampNs)
        compareAttributes(fake.attributes, observed.attributes)
        compareEvents(fake.events, observed.events)
        compareLinks(fake.links, observed.links)
        assertTrue(fake.hasEnded())
        compareResource(fake.resource, observed.resource)
    }

    private fun compareSpanContexts(expected: SpanContext, observed: SerializableSpanContext) {
        assertEquals(expected.traceId, observed.traceId)
        assertEquals(expected.spanId, observed.spanId)
        assertEquals(expected.traceState.asMap(), observed.traceState)
        assertEquals(expected.traceFlags.hex, observed.traceFlags)
    }

    private fun compareAttributes(expected: Map<String, Any>, observed: Map<String, String>) {
        assertEquals(expected.toStringMap(), observed)
    }

    private fun compareEvents(
        expected: List<ReadableSpanEvent>,
        observed: List<SerializableEventData>
    ) {
        assertEquals(expected.size, observed.size)

        expected.forEachIndexed { index, data ->
            val observedData = observed[index]
            assertEquals(data.name, observedData.name)
            assertEquals(data.timestamp, observedData.timestampNs)
            compareAttributes(data.attributes, observedData.attributes)
        }
    }

    private fun compareLinks(expected: List<ReadableLink>, observed: List<SerializableLinkData>) {
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
}
