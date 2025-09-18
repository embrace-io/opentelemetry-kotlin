package io.embrace.kotlin.opentelemetry.benchmark.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.SimpleLoggingFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class SimpleLoggingBenchmark {

    private lateinit var fixture: SimpleLoggingFixture

    @Setup
    fun setup() {
        fixture = SimpleLoggingFixture(createOpenTelemetry())
    }

    @Benchmark
    fun benchmarkSimpleLog() {
        fixture.execute()
    }
}
