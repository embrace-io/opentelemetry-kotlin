package io.embrace.kotlin.opentelemetry.benchmark.logging

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.OtelJavaLoggerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaLoggerCreationBenchmark {

    private lateinit var fixture: OtelJavaLoggerCreationFixture

    @Setup
    fun setup() {
        fixture = OtelJavaLoggerCreationFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkLoggerCreationOtelJava() {
        fixture.execute()
    }
}
