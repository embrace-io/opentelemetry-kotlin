package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelJavaHarness
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import org.junit.Assert.assertFalse
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SpanExportTest {

    private lateinit var harness: OtelJavaHarness
    private lateinit var tracerProvider: TracerProvider
    private lateinit var tracer: Tracer

    @BeforeTest
    fun setUp() {
        harness = OtelJavaHarness()
        tracerProvider = TracerProviderAdapter(harness.sdk)
        tracer = tracerProvider.getTracer("name", "version")
    }

    @Test
    fun `test minimal span export`() {
        val spanName = "my_span"
        tracer.createSpan(spanName).end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_minimal.json",
        )
    }

    @Test
    fun `test span properties export`() {
        val spanName = "my_span"
        val span = tracer.createSpan(spanName)
        assertEquals(spanName, span.name)

        val name = "new_name"
        span.name = name
        assertEquals(name, span.name)

        assertEquals(StatusCode.Unset, span.status)
        span.status = StatusCode.Ok
        assertEquals(StatusCode.Ok, span.status)

        assertEquals(SpanKind.INTERNAL, span.spanKind)
        span.spanKind = SpanKind.CLIENT // FIXME: future: not respected in exported spans
        assertEquals(SpanKind.CLIENT, span.spanKind)

        assertTrue(span.isRecording)
        span.end()
        assertFalse(span.isRecording)

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_props.json",
        )
    }
}
