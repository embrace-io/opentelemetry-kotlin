package io.embrace.kotlin.opentelemetry.benchmark.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class TracerCreationBenchmark {

    private lateinit var otel: OpenTelemetry

    @Setup
    fun setup() {
        otel = createOpenTelemetry()
    }

    @Benchmark
    fun benchmarkTracerCreation() {
        otel.tracerProvider.getTracer(
            "test",
            "0.1.0",
            "https://example.com/schema"
        ) {
            setStringAttribute("key", "value")
        }
    }
}
