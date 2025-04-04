package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.fakes.FakeLogRecordData
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.sdk.common.InstrumentationScopeInfo
import io.opentelemetry.sdk.resources.Resource
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SerializableLogRecordDataTest {

    @Test
    fun `test conversion`() {
        val fake = FakeLogRecordData()
        val observed = fake.toSerializable(true)

        compareResource(fake.resource, observed.resource)
        compareScope(fake.instrumentationScopeInfo, observed.instrumentationScopeInfo)
        assertEquals(fake.timestampEpochNanos, observed.timestampEpochNanos)
        assertEquals(fake.observedTimestampEpochNanos, observed.observedTimestampEpochNanos)
        compareSpanContexts(fake.spanContext, observed.spanContext)
        assertEquals(fake.severity.name, observed.severity)
        assertEquals(fake.severityText, observed.severityText)
        assertEquals(fake.body.asString(), observed.body)
        compareAttributes(fake.attributes, observed.attributes)
    }

    private fun compareResource(expected: Resource, observed: SerializableResource) {
        assertEquals("null", observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareScope(expected: InstrumentationScopeInfo, observed: SerializableInstrumentationScopeInfo) {
        assertEquals(expected.name, observed.name)
        assertEquals("null", observed.version)
        assertEquals("null", observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareSpanContexts(expected: SpanContext, observed: SerializableSpanContext) {
        assertEquals(expected.traceId, observed.traceId)
        assertEquals(expected.spanId, observed.spanId)
        assertEquals(expected.traceState.asMap(), observed.traceState)
        assertEquals(expected.traceFlags.asHex(), observed.traceFlags)
    }

    private fun compareAttributes(expected: Attributes, observed: Map<String, String>) {
        assertEquals(expected.toMap(), observed)
    }
}
