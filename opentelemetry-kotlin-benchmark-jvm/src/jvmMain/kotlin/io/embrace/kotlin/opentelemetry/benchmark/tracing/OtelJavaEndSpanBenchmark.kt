package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaSpanEndFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaEndSpanBenchmark {

    private lateinit var fixture: OtelJavaSpanEndFixture

    @Setup
    fun setup() {
        fixture = OtelJavaSpanEndFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkEndSpanOtelJava() {
        fixture.execute()
    }
}
