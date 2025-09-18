package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
class ComplexSpanCreationFixture(
    private val otel: OpenTelemetry
) : BenchmarkFixture {

    private val tracer = otel.tracerProvider.getTracer("test")
    private val other = tracer.createSpan("other")

    override fun execute() {
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
