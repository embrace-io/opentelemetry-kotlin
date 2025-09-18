package io.embrace.kotlin.opentelemetry.benchmark.logging

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.OtelJavaComplexLoggingFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaComplexLoggingBenchmark {

    private lateinit var fixture: OtelJavaComplexLoggingFixture

    @Setup
    fun setup() {
        fixture = OtelJavaComplexLoggingFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkComplexLogOtelJava() {
        fixture.execute()
    }
}
