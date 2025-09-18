package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CreateSimpleSpanBenchmark {

    private lateinit var otel: OtelJavaOpenTelemetry
    private lateinit var tracer: OtelJavaTracer

    @Setup
    fun setup() {
        otel = OtelJavaOpenTelemetrySdk.builder().build()
        tracer = otel.tracerProvider.get("test")
    }

    @Benchmark
    fun benchmarkSimpleSpan() {
        tracer.spanBuilder("new_span").startSpan()
    }
}
