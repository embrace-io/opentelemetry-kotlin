package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.AddSpanEventFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanEventFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CompatAddSpanEventBenchmark {

    private lateinit var fixture: AddSpanEventFixture

    @Setup
    fun setup() {
        fixture = AddSpanEventFixture(createCompatOpenTelemetry())
    }

    @Benchmark
    fun benchmarkAddEventCompat() {
        fixture.execute()
    }
}
