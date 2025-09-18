package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaSpanCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaCreateSimpleSpanBenchmark {

    private lateinit var fixture: OtelJavaSpanCreationFixture

    @Setup
    fun setup() {
        fixture = OtelJavaSpanCreationFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkSimpleSpanOtelJava() {
        fixture.execute()
    }
}
