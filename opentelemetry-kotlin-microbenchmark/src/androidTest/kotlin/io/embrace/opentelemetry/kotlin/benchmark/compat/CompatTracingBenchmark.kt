package io.embrace.opentelemetry.kotlin.benchmark.compat

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import io.embrace.opentelemetry.kotlin.tracing.addLink
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class CompatTracingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkTracerCreation() {
        val otel = createCompatOpenTelemetry()
        benchmarkRule.measureRepeated {
            otel.tracerProvider.getTracer(
                "test",
                "0.1.0",
                "https://example.com/schema"
            ) {
                setStringAttribute("key", "value")
            }
        }
    }

    @Test
    fun benchmarkSpanCreation() {
        val otel = createCompatOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        benchmarkRule.measureRepeated {
            tracer.createSpan("new_span")
        }
    }

    @Test
    fun benchmarkSpanEnd() {
        val otel = createCompatOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        val span = tracer.createSpan("new_span")

        benchmarkRule.measureRepeated {
            span.end()
        }
    }

    @Test
    fun benchmarkComplexSpanCreation() {
        val otel = createCompatOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        val other = tracer.createSpan("other")

        benchmarkRule.measureRepeated {
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

    @Test
    fun benchmarkAddSpanEvent() {
        val otel = createCompatOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        val span = tracer.createSpan("new_span")
        benchmarkRule.measureRepeated {
            span.addEvent("my_event") {
                setStringAttribute("key", "value")
            }
        }
    }

    @Test
    fun benchmarkAddSpanAttribute() {
        val otel = createCompatOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        val span = tracer.createSpan("new_span")
        benchmarkRule.measureRepeated {
            span.setStringAttribute("key", "value")
        }
    }

    @Test
    fun benchmarkAddSpanLink() {
        val otel = createCompatOpenTelemetry()
        val tracer = otel.tracerProvider.getTracer("test")
        val other = tracer.createSpan("other")
        val span = tracer.createSpan("new_span")
        benchmarkRule.measureRepeated {
            span.addLink(other) {
                setStringAttribute("key", "value")
            }
        }
    }
}
