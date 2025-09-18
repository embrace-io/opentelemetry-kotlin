package io.embrace.opentelemetry.kotlin.benchmark.tracing

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.AddSpanAttributeFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.AddSpanEventFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.AddSpanLinkFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.ComplexSpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanAttributeFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanEventFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanLinkFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaComplexSpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaSpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaSpanEndFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaTracerCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.SpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.SpanEndFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.TracerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class TracingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkTracerCreation() {
        val fixture = TracerCreationFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkSpanCreation() {
        val fixture = SpanCreationFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkSpanEnd() {
        val fixture = SpanEndFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkComplexSpanCreation() {
        val fixture = ComplexSpanCreationFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkAddSpanEvent() {
        val fixture = AddSpanEventFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkAddSpanAttribute() {
        val fixture = AddSpanAttributeFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkAddSpanLink() {
        val fixture = AddSpanLinkFixture(createOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }
}
