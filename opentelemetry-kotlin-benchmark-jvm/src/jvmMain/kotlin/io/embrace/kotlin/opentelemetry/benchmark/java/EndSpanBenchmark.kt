package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
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

    private lateinit var otel: OtelJavaOpenTelemetry
    private lateinit var tracer: OtelJavaTracer
    private lateinit var span: OtelJavaSpan

    @Setup
    fun setup() {
        otel = OtelJavaOpenTelemetrySdk.builder().build()
        tracer = otel.tracerProvider.get("test")
        span = tracer.spanBuilder("span").startSpan()
    }

    @Benchmark
    fun benchmarkEndSpan() {
        span.end()
    }
}
