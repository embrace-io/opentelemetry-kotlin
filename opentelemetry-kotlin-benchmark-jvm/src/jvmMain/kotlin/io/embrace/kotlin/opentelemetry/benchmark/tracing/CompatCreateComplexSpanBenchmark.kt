package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.ComplexSpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaComplexSpanCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CompatCreateComplexSpanBenchmark {

    private lateinit var fixture: ComplexSpanCreationFixture

    @Setup
    fun setup() {
        fixture = ComplexSpanCreationFixture(createCompatOpenTelemetry())
    }

    @Benchmark
    fun benchmarkCreateComplexSpanCompat() {
        fixture.execute()
    }
}
