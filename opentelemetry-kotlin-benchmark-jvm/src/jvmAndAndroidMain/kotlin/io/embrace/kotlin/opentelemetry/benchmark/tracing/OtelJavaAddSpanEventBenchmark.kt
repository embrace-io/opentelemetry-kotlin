package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanEventFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaAddSpanEventBenchmark {

    private lateinit var fixture: OtelJavaAddSpanEventFixture

    @Setup
    fun setup() {
        fixture = OtelJavaAddSpanEventFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkAddEventOtelJava() {
        fixture.execute()
    }
}
