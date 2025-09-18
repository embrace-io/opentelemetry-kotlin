package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class AddSpanAttributeBenchmark {

    private lateinit var span: OtelJavaSpan

    @Setup
    fun setup() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        span = tracer.spanBuilder("new_span").startSpan()
    }

    @Benchmark
    fun benchmarkAddAttribute() {
        val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("key"), "value")
        span.addEvent("my_event", attrs)
    }
}
