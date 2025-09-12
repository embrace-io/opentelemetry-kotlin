package io.embrace.opentelemetry.kotlin.framework.serialization

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.framework.serialization.conversion.toSerializable
import io.embrace.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class SerializableLogRecordDataTest {

    @Test
    fun testConversion() {
        val fake = FakeReadableLogRecord()
        val observed = fake.toSerializable()

        compareResource(checkNotNull(fake.resource), checkNotNull(observed.resource))
        compareScope(
            checkNotNull(fake.instrumentationScopeInfo),
            checkNotNull(observed.instrumentationScopeInfo)
        )
        assertEquals(fake.timestamp, observed.timestampEpochNanos)
        assertEquals(fake.observedTimestamp, observed.observedTimestampEpochNanos)
        compareSpanContexts(fake.spanContext, observed.spanContext)
        assertEquals(fake.severityNumber?.name, observed.severity)
        assertEquals(fake.severityText, observed.severityText)
        assertEquals(fake.body, observed.body)
        compareAttributes(fake.attributes.mapValues { it.value.toString() }, observed.attributes)
    }

    private fun compareResource(expected: Resource, observed: SerializableResource) {
        assertEquals(expected.schemaUrl, observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareScope(
        expected: InstrumentationScopeInfo,
        observed: SerializableInstrumentationScopeInfo
    ) {
        assertEquals(expected.name, observed.name)
        assertEquals(expected.version, observed.version)
        assertEquals(expected.schemaUrl, observed.schemaUrl)
        assertEquals(expected.attributes.toMap(), observed.attributes)
    }

    private fun compareSpanContexts(expected: SpanContext, observed: SerializableSpanContext) {
        assertEquals(expected.traceId, observed.traceId)
        assertEquals(expected.spanId, observed.spanId)
        assertEquals(expected.traceState.asMap(), observed.traceState)
        assertEquals(expected.traceFlags.hex, observed.traceFlags)
    }

    private fun compareAttributes(expected: Map<String, String>, observed: Map<String, String>) {
        assertEquals(expected, observed)
    }
}
