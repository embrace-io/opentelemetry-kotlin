package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaSpanExporter
import io.embrace.opentelemetry.kotlin.fakes.otel.kotlin.FakeReadableSpan
import io.embrace.opentelemetry.kotlin.k2j.tracing.convertToOtelJava
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanExporterAdapterTest {

    private lateinit var impl: FakeOtelJavaSpanExporter
    private lateinit var wrapper: OtelJavaSpanExporterAdapter

    @Before
    fun setUp() {
        impl = FakeOtelJavaSpanExporter()
        wrapper = OtelJavaSpanExporterAdapter(impl)
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
        val original = FakeReadableSpan()
        assertEquals(OperationResultCode.Success, wrapper.export(listOf(original)))

        val observed = impl.exports.single()
        assertEquals(original.name, observed.name)
        assertEquals(original.status.convertToOtelJava(), observed.status.statusCode)
        assertEquals(original.parent.spanId, observed.parentSpanContext.spanId)
        assertEquals(original.spanContext.spanId, observed.spanContext.spanId)
        assertEquals(original.spanKind.convertToOtelJava(), observed.kind)
        assertEquals(original.startTimestamp, observed.startEpochNanos)
        assertEquals(original.attributes, observed.attributes.toMap())
        assertEquals(original.resource.attributes, observed.resource.attributes.toMap())

        val originalScope = original.instrumentationScopeInfo
        val observedScope = observed.instrumentationScopeInfo
        assertEquals(originalScope.name, observedScope.name)
        assertEquals(originalScope.version, observedScope.version)
        assertEquals(originalScope.schemaUrl, observedScope.schemaUrl)
        assertEquals(emptyMap(), observedScope.attributes.toMap()) // otel-java don't support this

        val originalEvent = original.events.single()
        val observedEvent = observed.events.single()
        assertEquals(originalEvent.name, observedEvent.name)
        assertEquals(originalEvent.attributes, observedEvent.attributes.toMap())

        val originalLink = original.links.single()
        val observedLink = observed.links.single()
        assertEquals(originalLink.spanContext.spanId, observedLink.spanContext.spanId)
        assertEquals(originalLink.attributes, observedLink.attributes.toMap())
    }
}
