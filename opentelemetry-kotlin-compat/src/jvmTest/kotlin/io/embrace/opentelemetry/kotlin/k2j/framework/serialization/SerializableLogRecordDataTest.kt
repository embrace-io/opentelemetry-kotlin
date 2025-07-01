package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaResource
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.fakes.FakeLogRecordData
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
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

    private fun compareResource(expected: OtelJavaResource, observed: SerializableResource) {
        assertEquals("null", observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareScope(expected: OtelJavaInstrumentationScopeInfo, observed: SerializableInstrumentationScopeInfo) {
        assertEquals(expected.name, observed.name)
        assertEquals("null", observed.version)
        assertEquals("null", observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareSpanContexts(expected: OtelJavaSpanContext, observed: SerializableSpanContext) {
        assertEquals(expected.traceId, observed.traceId)
        assertEquals(expected.spanId, observed.spanId)
        assertEquals(expected.traceState.asMap(), observed.traceState)
        assertEquals(expected.traceFlags.asHex(), observed.traceFlags)
    }

    private fun compareAttributes(expected: OtelJavaAttributes, observed: Map<String, String>) {
        assertEquals(expected.toMap(), observed)
    }
}
