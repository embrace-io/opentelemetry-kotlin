package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry

@OptIn(ExperimentalApi::class)
class OtelJavaAddSpanAttributeFixture(
    otel: OtelJavaOpenTelemetry
) : BenchmarkFixture {

    private val tracer = otel.tracerProvider.get("test")
    private val span = tracer.spanBuilder("new_span").startSpan()

    override fun execute() {
        span.setAttribute("key", "value")
    }
}
