package io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind

@OptIn(ExperimentalApi::class)
class OtelJavaComplexSpanCreationFixture(
    otel: OtelJavaOpenTelemetry
) : BenchmarkFixture {

    private val tracer = otel.tracerProvider.get("test")
    private val other = tracer.spanBuilder("other").startSpan()

    override fun execute() {
        val builder = tracer.spanBuilder("new_span")
            .setParent(OtelJavaContext.root())
            .setSpanKind(OtelJavaSpanKind.CLIENT)

        repeat(100) { k ->
            builder.setAttribute("key_$k", "value")
            val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("link_$k"), "value")
            builder.addLink(other.spanContext, attrs)
        }

        val span = builder.startSpan()

        repeat(100) { k ->
            val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("key"), "value")
            span.addEvent("my_event_$k", attrs)
        }
    }
}
