package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerProvider
import io.embrace.opentelemetry.kotlin.j2k.tracing.OtelJavaTracerProviderAdapter
import io.embrace.opentelemetry.kotlin.k2j.framework.OtelJavaHarness
import kotlin.test.Test

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanExportTest {

    private lateinit var harness: OtelJavaHarness
    private lateinit var tracerProvider: OtelJavaTracerProvider
    private lateinit var tracer: OtelJavaTracer

    // TODO: future: requires implementation of TracerProvider + exporter infrastructure before test can be written
    @Test(expected = NotImplementedError::class)
    fun `test minimal span export`() {
        harness = OtelJavaHarness()
        tracerProvider = OtelJavaTracerProviderAdapter(harness.tracerProvider)
        tracer = tracerProvider.get("name", "version")
        val spanName = "my_span"
        tracer.spanBuilder(spanName).startSpan().end()

        harness.assertSpans(
            expectedCount = 1,
            goldenFileName = "span_minimal.json",
        )
    }
}
