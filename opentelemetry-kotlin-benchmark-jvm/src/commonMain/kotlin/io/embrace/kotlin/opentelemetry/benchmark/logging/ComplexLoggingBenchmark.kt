package io.embrace.kotlin.opentelemetry.benchmark.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.ComplexLoggingFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.LoggerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class ComplexLoggingBenchmark {

    private lateinit var fixture: ComplexLoggingFixture

    @Setup
    fun setup() {
        fixture = ComplexLoggingFixture(createOpenTelemetry())
    }

    @Benchmark
    fun benchmarkComplexLog() {
        fixture.execute()
    }
}
