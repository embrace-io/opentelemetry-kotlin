package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.k2j.framework.OtelJavaHarness
import io.embrace.opentelemetry.kotlin.k2j.framework.assertSpans
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
    fun `test basic span export`() {
        val spanName = "my_span"
        tracer.createSpan(spanName).end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "basic_span.json",
            spanDataAssertions = {
                assertEquals(spanName, single().name)
            }
        )
    }
}
