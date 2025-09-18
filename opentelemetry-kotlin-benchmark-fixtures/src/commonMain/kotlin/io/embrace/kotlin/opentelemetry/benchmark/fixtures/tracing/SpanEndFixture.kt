package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry

@OptIn(ExperimentalApi::class)
class SpanEndFixture(
    otel: OpenTelemetry
) : BenchmarkFixture {

    private val tracer = otel.tracerProvider.getTracer("test")
    private val span = tracer.createSpan("new_span")

    override fun execute() {
        span.end()
    }
}
