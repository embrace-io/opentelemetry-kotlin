package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.k2j.OtelJavaHarness
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SpanExportTest {

    private lateinit var harness: OtelJavaHarness

    @BeforeTest
    fun setUp() {
        harness = OtelJavaHarness()
    }

    @Test
    fun `test span export`() {
        val tracerProvider = TracerProviderAdapter(harness.sdk)
        val tracer = tracerProvider.getTracer("name", "version")
        val spanName = "my_span"
        tracer.createSpan(spanName).end()
        val observedSpan = harness.awaitSpans(1).single()
        assertEquals(spanName, observedSpan.name)
    }
}
