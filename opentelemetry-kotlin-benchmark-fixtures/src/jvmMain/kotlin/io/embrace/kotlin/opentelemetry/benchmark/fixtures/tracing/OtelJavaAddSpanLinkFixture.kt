package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry

@OptIn(ExperimentalApi::class)
class OtelJavaAddSpanLinkFixture(
    otel: OtelJavaOpenTelemetry
) : BenchmarkFixture {

    private val tracer = otel.tracerProvider.get("test")
    private val span = tracer.spanBuilder("new_span").startSpan()
    private val other = tracer.spanBuilder("other").startSpan()

    override fun execute() {
        val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("key"), "value")
        span.addLink(other.spanContext, attrs)
    }
}
