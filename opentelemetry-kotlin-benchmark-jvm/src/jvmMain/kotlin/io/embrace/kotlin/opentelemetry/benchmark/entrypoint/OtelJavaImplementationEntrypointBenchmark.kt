package io.embrace.kotlin.opentelemetry.benchmark.entrypoint

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaImplementationEntrypointBenchmark {

    @Benchmark
    fun benchmarkEntrypoint() {
        OtelJavaOpenTelemetrySdk.builder().build()
    }
}