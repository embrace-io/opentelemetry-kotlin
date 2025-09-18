package io.embrace.kotlin.opentelemetry.benchmark.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CreateComplexSpanBenchmark {

    private lateinit var otel: OpenTelemetry
    private lateinit var tracer: Tracer
    private lateinit var other: Span

    @Setup
    fun setup() {
        otel = createOpenTelemetry()
        tracer = otel.tracerProvider.getTracer("test")
        other = tracer.createSpan("other")
    }

    @Benchmark
    fun benchmarkComplexSpan() {
        tracer.createSpan(
            "new_span",
            otel.contextFactory.root(),
            SpanKind.CLIENT,
        ) {
            setStringAttribute("key", "value")
            addEvent("my_event") {
                setBooleanAttribute("event", true)
            }
            addLink(other.spanContext)
        }
    }
}
