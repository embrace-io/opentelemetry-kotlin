package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaTracerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaTracerCreationBenchmark {

    private lateinit var fixture: OtelJavaTracerCreationFixture

    @Setup
    fun setup() {
        fixture = OtelJavaTracerCreationFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkTracerCreationOtelJava() {
        fixture.execute()
    }
}
