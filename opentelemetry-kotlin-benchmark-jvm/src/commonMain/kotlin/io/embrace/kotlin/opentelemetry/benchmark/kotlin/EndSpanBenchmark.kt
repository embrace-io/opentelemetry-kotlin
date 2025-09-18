package io.embrace.kotlin.opentelemetry.benchmark.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class EndSpanBenchmark {

    private lateinit var otel: OpenTelemetry
    private lateinit var span: Span

    @Setup
    fun setup() {
        otel = createOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        span = tracer.createSpan("new_span")
    }

    @Benchmark
    fun benchmarkEndSpan() {
        span.end()
    }
}
