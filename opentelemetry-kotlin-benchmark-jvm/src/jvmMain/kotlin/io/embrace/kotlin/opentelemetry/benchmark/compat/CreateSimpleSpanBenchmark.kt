package io.embrace.kotlin.opentelemetry.benchmark.compat

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CreateSimpleSpanBenchmark {

    private lateinit var otel: OpenTelemetry
    private lateinit var tracer: Tracer

    @Setup
    fun setup() {
        otel = createCompatOpenTelemetry()
        tracer = otel.tracerProvider.getTracer("test")
    }

    @Benchmark
    fun benchmarkSimpleSpan() {
        tracer.createSpan("new_span")
    }
}
