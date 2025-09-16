package io.embrace.opentelemetry.kotlin.benchmark.java

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class OtelJavaTracingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkTracerCreation() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        benchmarkRule.measureRepeated {
            // note: not possible to pass version/schemaUrl/attributes in opentelemetry-java
            otel.tracerProvider.get("test")
        }
    }

    @Test
    fun benchmarkSpanCreation() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        benchmarkRule.measureRepeated {
            tracer.spanBuilder("new_span").startSpan()
        }
    }

    @Test
    fun benchmarkSpanEnd() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        val span = tracer.spanBuilder("new_span").startSpan()

        benchmarkRule.measureRepeated {
            span.end()
        }
    }

    @Test
    fun benchmarkComplexSpanCreation() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        val other = tracer.spanBuilder("other").startSpan()

        benchmarkRule.measureRepeated {
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

    @Test
    fun benchmarkAddSpanEvent() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        val span = tracer.spanBuilder("new_span").startSpan()
        benchmarkRule.measureRepeated {
            val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("key"), "value")
            span.addEvent("my_event", attrs)
        }
    }

    @Test
    fun benchmarkAddSpanAttribute() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        val span = tracer.spanBuilder("new_span").startSpan()
        benchmarkRule.measureRepeated {
            span.setAttribute("key", "value")
        }
    }

    @Test
    fun benchmarkAddSpanLink() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val tracer = otel.tracerProvider.get("test")
        val other = tracer.spanBuilder("other").startSpan()
        val span = tracer.spanBuilder("new_span").startSpan()
        benchmarkRule.measureRepeated {
            val attrs = OtelJavaAttributes.of(OtelJavaAttributeKey.stringKey("key"), "value")
            span.addLink(other.spanContext, attrs)
        }
    }
}
