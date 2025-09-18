package io.embrace.kotlin.opentelemetry.benchmark.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.LoggerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class LoggerCreationBenchmark {

    private lateinit var fixture: LoggerCreationFixture

    @Setup
    fun setup() {
        fixture = LoggerCreationFixture(createOpenTelemetry())
    }

    @Benchmark
    fun benchmarkLoggerCreation() {
        fixture.execute()
    }
}
