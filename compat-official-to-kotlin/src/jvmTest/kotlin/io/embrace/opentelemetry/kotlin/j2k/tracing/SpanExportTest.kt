package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.framework.OtelJavaHarness
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.TracerProvider
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class SpanExportTest {

    private lateinit var harness: OtelJavaHarness
    private lateinit var tracerProvider: TracerProvider
    private lateinit var tracer: Tracer

    // TODO: future: requires implementation of TracerProvider + exporter infrastructure before test can be written
    @Test(expected = NotImplementedError::class)
    fun `test minimal span export`() {
        harness = OtelJavaHarness()
        tracerProvider = TracerProviderAdapter(harness.tracerProvider)
        tracer = tracerProvider.get("name", "version")
        val spanName = "my_span"
        tracer.spanBuilder(spanName).startSpan().end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_minimal.json",
        )
    }
}
