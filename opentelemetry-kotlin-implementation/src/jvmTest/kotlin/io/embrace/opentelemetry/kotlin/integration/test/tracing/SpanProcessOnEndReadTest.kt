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
internal class SpanProcessOnEndReadTest {

    private lateinit var harness: IntegrationTestHarness

    @BeforeTest
    fun setUp() {
        harness = IntegrationTestHarness()
    }

    @Test
    fun `test read properties in SpanProcessor onEnd`() {
        harness.config.spanProcessors.add(OnEndSpanProcessor())
        harness.tracer.createSpan("span") {
            setStringAttribute("key", "value")
            addEvent("test")
            addLink(FakeSpanContext()) {
                setStringAttribute("foo", "bar")
            }
        }.end()
        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_read_on_end.json",
        )
    }

    private class OnEndSpanProcessor : SpanProcessor {
        override fun onStart(
            span: ReadWriteSpan,
            parentContext: Context
        ) {
            span.handleSpan()
        }

        override fun onEnd(span: ReadableSpan) {
        }

        private fun ReadableSpan.handleSpan() {
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
            assertEquals("test", events.single().name)
            assertEquals("bar", links.single().attributes["foo"])
        }

        override fun isStartRequired(): Boolean = true
        override fun isEndRequired(): Boolean = true
        override fun forceFlush(): OperationResultCode = OperationResultCode.Success
        override fun shutdown(): OperationResultCode = OperationResultCode.Success
    }
}
