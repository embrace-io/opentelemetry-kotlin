package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class LoggerCreationBenchmark {

    private lateinit var otel: OtelJavaOpenTelemetry

    @Setup
    fun setup() {
        otel = OtelJavaOpenTelemetrySdk.builder().build()
    }

    @Benchmark
    fun benchmarkLoggerCreation() {
        // note: not possible to pass version/schemaUrl/attributes in opentelemetry-java
        otel.logsBridge.get("test")
    }
}
