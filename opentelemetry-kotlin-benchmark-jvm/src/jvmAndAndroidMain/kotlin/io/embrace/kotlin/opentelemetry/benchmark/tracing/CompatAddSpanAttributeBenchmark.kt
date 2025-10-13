package io.embrace.kotlin.opentelemetry.benchmark.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.AddSpanAttributeFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanAttributeFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CompatAddSpanAttributeBenchmark {

    private lateinit var fixture: AddSpanAttributeFixture

    @Setup
    fun setup() {
        fixture = AddSpanAttributeFixture(createCompatOpenTelemetry())
    }

    @Benchmark
    fun benchmarkAddAttributeCompat() {
        fixture.execute()
    }
}
