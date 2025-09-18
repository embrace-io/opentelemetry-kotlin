package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

@OptIn(ExperimentalApi::class)
class TracerCreationFixture(
    private val otel: OpenTelemetry
) : BenchmarkFixture {

    override fun execute() {
        otel.tracerProvider.getTracer(
            "test",
            "0.1.0",
            "https://example.com/schema"
        ) {
            setStringAttribute("key", "value")
        }
    }
}
