package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.tracing.addLink

@OptIn(ExperimentalApi::class)
class AddSpanLinkFixture(
    otel: OpenTelemetry
) : BenchmarkFixture {

    private val tracer = otel.tracerProvider.getTracer("test")
    private val other = tracer.createSpan("other")
    private val span = tracer.createSpan("new_span")

    override fun execute() {
        span.addLink(other) {
            setStringAttribute("key", "value")
        }
    }
}
