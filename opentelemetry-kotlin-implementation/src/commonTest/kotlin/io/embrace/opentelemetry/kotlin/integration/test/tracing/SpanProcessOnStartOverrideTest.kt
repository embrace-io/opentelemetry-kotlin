package io.embrace.opentelemetry.kotlin.integration.test.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.export.OperationResultCode
import io.embrace.opentelemetry.kotlin.integration.test.IntegrationTestHarness
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalApi::class)
internal class SpanProcessOnStartOverrideTest {

    private lateinit var harness: IntegrationTestHarness

    @BeforeTest
    fun setUp() {
        harness = IntegrationTestHarness()
    }

    @Test
    fun testOverridePropertiesInProcessor() {
        harness.config.spanProcessors.add(OnStartSpanProcessor())
        harness.tracer.createSpan("span") {
            setStringAttribute("key", "value")
            addEvent("test")
            addLink(FakeSpanContext())
        }
        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_override_on_start.json",
        )
    }

    private class OnStartSpanProcessor : SpanProcessor {
        override fun onStart(
            span: ReadWriteSpan,
            parentContext: Context
        ) {
            span.handleSpan()
        }

        private fun ReadWriteSpan.handleSpan() {
            // assert properties can be read
            assertEquals("span", name)
            assertEquals(StatusData.Unset, status)
            assertFalse(hasEnded)
            assertEquals(SpanKind.INTERNAL, spanKind)
            assertEquals(0, startTimestamp)
            assertNull(endTimestamp)
            assertTrue(spanContext.isValid)
            assertFalse(parent.isValid)
            assertTrue(resource.attributes.isEmpty())
            assertEquals("test_tracer", instrumentationScopeInfo.name)
            assertEquals(mapOf("key" to "value"), attributes)
            assertEquals(1, events.size)
            assertEquals(1, links.size)

            // assert subset of properties can be written
            name = "override"
            status = StatusData.Error("override")
            setStringAttribute("foo", "bar")
            addEvent("test", 5) {
                setStringAttribute("foo", "bar")
            }
            addLink(FakeSpanContext()) {
                setStringAttribute("foo", "bar")
            }
            end(678)
        }

        override fun onEnd(span: ReadableSpan) {
        }

        override fun isStartRequired(): Boolean = true
        override fun isEndRequired(): Boolean = true
        override fun forceFlush(): OperationResultCode = OperationResultCode.Success
        override fun shutdown(): OperationResultCode = OperationResultCode.Success
    }
}
