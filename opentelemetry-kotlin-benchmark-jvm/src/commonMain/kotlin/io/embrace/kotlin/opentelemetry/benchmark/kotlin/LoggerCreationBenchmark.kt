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
class LoggerCreationBenchmark {

    private lateinit var otel: OpenTelemetry

    @Setup
    fun setup() {
        otel = createOpenTelemetry()
    }

    @Benchmark
    fun benchmarkLoggerCreation() {
        otel.loggerProvider.getLogger(
            "test",
            "0.1.0",
            "https://example.com/schema"
        ) {
            setStringAttribute("key", "value")
        }
    }
}
