package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class CreateComplexSpanBenchmark {

    private lateinit var tracer: OtelJavaTracer
    private lateinit var other: OtelJavaSpan

    @Setup
    fun setup() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        tracer = otel.tracerProvider.get("test")
        other = tracer.spanBuilder("other").startSpan()
    }

    @Benchmark
    fun benchmarkComplexSpan() {
        val span = tracer.spanBuilder("new_span")
            .setParent(OtelJavaContext.root())
            .setSpanKind(OtelJavaSpanKind.CLIENT)
            .setAttribute("key", "value")
            .addLink(other.spanContext)
            .startSpan()
        val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("key"), "value")
        span.addEvent("my_event", attrs)
    }
}
