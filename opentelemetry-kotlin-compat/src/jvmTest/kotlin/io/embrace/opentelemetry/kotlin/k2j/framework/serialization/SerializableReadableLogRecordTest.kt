package io.embrace.opentelemetry.kotlin.k2j.framework.serialization

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeReadableLogRecord
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.k2j.framework.serialization.conversion.toStringMap
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SerializableReadableLogRecordTest {

    @Test
    fun `test conversion`() {
        val fake = FakeReadableLogRecord()
        val observed = fake.toSerializable(true)

        compareResource(checkNotNull(fake.resource), observed.resource)
        compareScope(checkNotNull(fake.instrumentationScopeInfo), observed.instrumentationScopeInfo)
        assertEquals(fake.timestamp, observed.timestampEpochNanos)
        assertEquals(fake.observedTimestamp, observed.observedTimestampEpochNanos)
        compareSpanContexts(fake.spanContext, observed.spanContext)
        assertEquals(checkNotNull(fake.severityNumber).name, observed.severity)
        assertEquals(fake.severityText, observed.severityText)
        assertEquals(fake.body, observed.body)
        compareAttributes(fake.attributes, observed.attributes)
    }

    private fun compareResource(expected: Resource, observed: SerializableResource) {
        assertEquals("null", observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareScope(
        expected: InstrumentationScopeInfo,
        observed: SerializableInstrumentationScopeInfo
    ) {
        assertEquals(expected.name, observed.name)
        assertEquals("null", observed.version)
        assertEquals("null", observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
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
}
