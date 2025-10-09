package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanLinkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class OtelJavaAddSpanLinkBenchmark {

    private lateinit var fixture: OtelJavaAddSpanLinkFixture

    @Setup
    fun setup() {
        fixture = OtelJavaAddSpanLinkFixture(createOtelJavaOpenTelemetry())
    }

    @Benchmark
    fun benchmarkAddLinkOtelJava() {
        fixture.execute()
    }
}
