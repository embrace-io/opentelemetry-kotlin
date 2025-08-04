package io.embrace.opentelemetry.kotlin.tracing.model

import fakeInProgressOtelJavaSpanData
import fakeOtelJavaEventData
import fakeOtelJavaLinkData
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanData
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaStatusData
import io.embrace.opentelemetry.kotlin.attributes.attrsFromMap
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaReadWriteSpan
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaReadableSpan
import io.embrace.opentelemetry.kotlin.fakes.otel.java.FakeOtelJavaSpanData
import io.embrace.opentelemetry.kotlin.framework.OtelKotlinHarness
import io.embrace.opentelemetry.kotlin.scope.toOtelJavaInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.testing.common.TestSpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaEventData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaLinkData
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.ext.toOtelJavaStatusData
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@OptIn(ExperimentalApi::class)
internal class ReadWriteSpanAdapterTest {

    private lateinit var harness: OtelKotlinHarness
    private lateinit var adapter: ReadWriteSpanAdapter
    private lateinit var fakeReadableSpan: FakeOtelJavaReadableSpan
    private lateinit var fakeImpl: FakeOtelJavaReadWriteSpan

    @BeforeTest
    fun setUp() {
        fakeReadableSpan = FakeOtelJavaReadableSpan(fakeInProgressOtelJavaSpanData)
        fakeImpl = FakeOtelJavaReadWriteSpan(fakeReadableSpan)
        adapter = ReadWriteSpanAdapter(fakeImpl)
        harness = OtelKotlinHarness()
    }

    @Test
    fun `pass through of initial state`() {
        with(fakeImpl.toSpanData()) {
            adapter.assertImmutableProperties(this)
            adapter.assertMutableProperties(this)
        }
    }

    @Test
    fun `mutable properties change as implementation changes`() {
        val initialState = fakeImpl.toSpanData()
        fakeReadableSpan.otelJavaSpanData = FakeOtelJavaSpanData(
            implName = "new${initialState.name}",
            implSpanContext = initialState.spanContext,
            implParentSpanContext = initialState.parentSpanContext,
            implSpanKind = initialState.kind,
            implAttributes = attrsFromMap(initialState.attributes.toMap() + mapOf("newattr" to "value")),
            implEventData = initialState.events + fakeOtelJavaEventData,
            implLinkData = initialState.links + fakeOtelJavaLinkData,
            implStartNs = initialState.startEpochNanos,
            implEndNs = initialState.startEpochNanos + 5_000_000,
            implEnded = true,
            implStatusData = OtelJavaStatusData.error(),
            implResource = initialState.resource
        )

        val modifiedState = fakeImpl.toSpanData()

        adapter.assertImmutableProperties(initialState)
        adapter.assertMutableProperties(modifiedState)
    }

    @Test
    fun `span updatable`() {
        val newStatus = StatusData.Error("err")
        val processor = TestSpanProcessor(
            runOnStart = assertReadWriteSpan(
                updateCode = { span ->
                    span.apply {
                        name = "new-name"
                        status = newStatus
                        setStringAttribute("key", "value")
                    }
                },
                expectedName = "new-name",
                expectedStatus = StatusData.Error("err"),
                expectedAttributes = mapOf("key" to "value")
            ),
            runOnEnd = assertReadableSpan(
                expectedName = "name",
                expectedStatus = StatusData.Error("err"),
                expectedAttributes = mapOf("key" to "value")
            ),
        )
        harness.config.spanProcessors.add(processor)
        harness.tracer.createSpan("name").end()
        harness.assertSpans(
            expectedCount = 1,
            assertions = { spans ->
                with(spans.single()) {
                    assertEquals("name", name)
                    assertEquals(newStatus.statusCode.name, statusData.name)
                    assertEquals(newStatus.description, statusData.description)
                    assertEquals(mapOf("key" to "value"), attributes)
                }
            }
        )
    }

    private fun ReadWriteSpanAdapter.assertImmutableProperties(expected: OtelJavaSpanData) {
        assertEquals(expected.spanContext, spanContext.toOtelJavaSpanContext())
        assertEquals(expected.parentSpanContext, parent.toOtelJavaSpanContext())
        assertEquals(expected.kind, spanKind.toOtelJavaSpanKind())
        assertEquals(expected.startEpochNanos, startTimestamp)
        assertEquals(expected.resource.attributes.toMap(), resource.attributes)
        assertEquals(expected.resource.schemaUrl, resource.schemaUrl)
        assertEquals(expected.instrumentationScopeInfo, instrumentationScopeInfo.toOtelJavaInstrumentationScopeInfo())
    }

    private fun ReadWriteSpanAdapter.assertMutableProperties(expected: OtelJavaSpanData) {
        assertEquals(expected.name, name)
        assertEquals(expected.status, status.toOtelJavaStatusData())
        assertEquals(expected.hasEnded(), hasEnded)
        assertEquals(expected.endEpochNanos, endTimestamp)
        assertEquals(expected.attributes.toMap(), attributes)
        assertEquals(expected.events, events.map { it.toOtelJavaEventData() })
        assertEquals(expected.links, links.map { it.toOtelJavaLinkData() })
    }

    private fun assertReadWriteSpan(
        updateCode: (span: ReadWriteSpan) -> Unit,
        expectedName: String? = null,
        expectedStatus: StatusData? = null,
        expectedAttributes: Map<String, Any>? = null,
        expectedEvents: List<EventData>? = null,
        expectedLinks: List<LinkData>? = null,
    ): (span: ReadWriteSpan) -> Unit {
        return fun(span: ReadWriteSpan) {
            updateCode(span)
            assertReadableSpan(
                expectedName = expectedName,
                expectedStatus = expectedStatus,
                expectedAttributes = expectedAttributes,
                expectedEvents = expectedEvents,
                expectedLinks = expectedLinks
            ).invoke(span)
        }
    }

    private fun assertReadableSpan(
        expectedName: String? = null,
        expectedStatus: StatusData? = null,
        expectedAttributes: Map<String, Any>? = null,
        expectedEvents: List<EventData>? = null,
        expectedLinks: List<LinkData>? = null,
    ): (span: ReadableSpan) -> Unit {
        return fun(readableSpan: ReadableSpan) {
            with(readableSpan) {
                if (expectedName != null) {
                    assertEquals(expectedName, name)
                }

                if (expectedStatus != null) {
                    assertEquals(expectedStatus.statusCode, status.statusCode)
                    assertEquals(expectedStatus.description, status.description)
                }

                if (expectedAttributes != null) {
                    assertEquals(expectedAttributes, attributes)
                }

                if (expectedEvents != null) {
                    assertEquals(expectedEvents, events)
                }

                if (expectedLinks != null) {
                    assertEquals(expectedLinks, links)
                }
            }
        }
    }
}
