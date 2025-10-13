package io.embrace.kotlin.opentelemetry.benchmark.entrypoint

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CompatEntrypointBenchmark {

    @Benchmark
    fun benchmarkEntrypointCompat() {
        createCompatOpenTelemetry()
    }
}