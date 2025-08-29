package io.opentelemetry.kotlin.logging.export

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.toMap
import io.opentelemetry.kotlin.export.OperationResultCode
import io.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaLogRecordExporter
import io.opentelemetry.kotlin.logging.model.FakeReadableLogRecord
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class OtelJavaLogRecordExporterAdapterTest {

    private lateinit var impl: FakeOtelJavaLogRecordExporter
    private lateinit var wrapper: OtelJavaLogRecordExporterAdapter

    @Before
    fun setUp() {
        impl = FakeOtelJavaLogRecordExporter()
        wrapper = OtelJavaLogRecordExporterAdapter(impl)
    }

    @Test
    fun `test flush`() {
        assertEquals(OperationResultCode.Success, wrapper.forceFlush())
        assertEquals(1, impl.flushCount)
    }

    @Test
    fun `test shutdown`() {
        assertEquals(OperationResultCode.Success, wrapper.shutdown())
        assertEquals(1, impl.shutdownCount)
    }

    @Test
    fun `test export`() {
        val original = FakeReadableLogRecord()
        assertEquals(OperationResultCode.Success, wrapper.export(listOf(original)))

        val observed = impl.exports.single()
        assertEquals(original.timestamp, observed.timestampEpochNanos)
        assertEquals(original.observedTimestamp, observed.observedTimestampEpochNanos)
        assertEquals(original.severityText, observed.severityText)
        assertEquals(original.severityNumber?.severityNumber, observed.severity.severityNumber)
        assertEquals(original.body, observed.bodyValue?.asString())
        assertEquals(original.attributes, observed.attributes.toMap())
        assertEquals(original.resource.attributes, observed.resource.attributes.toMap())
        assertEquals(original.spanContext.spanId, observed.spanContext.spanId)

        val originalScope = original.instrumentationScopeInfo
        val observedScope = observed.instrumentationScopeInfo
        assertEquals(originalScope.name, observedScope.name)
        assertEquals(originalScope.version, observedScope.version)
        assertEquals(originalScope.schemaUrl, observedScope.schemaUrl)
        assertEquals(originalScope.attributes, observedScope.attributes.toMap())
    }
}
