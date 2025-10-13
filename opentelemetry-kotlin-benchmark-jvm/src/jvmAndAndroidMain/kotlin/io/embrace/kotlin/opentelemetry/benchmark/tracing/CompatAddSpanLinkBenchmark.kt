package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.AddSpanLinkFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanLinkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CompatAddSpanLinkBenchmark {

    private lateinit var fixture: AddSpanLinkFixture

    @Setup
    fun setup() {
        fixture = AddSpanLinkFixture(createCompatOpenTelemetry())
    }

    @Benchmark
    fun benchmarkAddLinkCompat() {
        fixture.execute()
    }
}
