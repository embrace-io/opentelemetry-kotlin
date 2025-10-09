package io.embrace.kotlin.opentelemetry.benchmark.logging

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.OtelJavaSimpleLoggingFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaSimpleLoggingBenchmark {

    private lateinit var fixture: OtelJavaSimpleLoggingFixture

    @Setup
    fun setup() {
        fixture = OtelJavaSimpleLoggingFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkSimpleLogOtelJava() {
        fixture.execute()
    }
}
