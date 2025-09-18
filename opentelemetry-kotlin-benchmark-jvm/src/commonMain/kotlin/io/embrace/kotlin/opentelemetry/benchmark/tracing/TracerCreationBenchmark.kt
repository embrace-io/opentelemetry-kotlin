package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaTracerCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.TracerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class TracerCreationBenchmark {


    private lateinit var fixture: TracerCreationFixture

    @Setup
    fun setup() {
        fixture = TracerCreationFixture(createOpenTelemetry())
    }

    @Benchmark
    fun benchmarkTracerCreation() {
        fixture.execute()
    }
}
