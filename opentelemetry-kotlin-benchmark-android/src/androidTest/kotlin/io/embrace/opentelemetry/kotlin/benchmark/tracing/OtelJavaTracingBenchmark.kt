package io.embrace.opentelemetry.kotlin.benchmark.tracing

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.kotlin.opentelemetry.benchmark.createOtelJavaOpenTelemetry
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanAttributeFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanEventFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaAddSpanLinkFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaComplexSpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaSpanCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaSpanEndFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.tracing.OtelJavaTracerCreationFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class OtelJavaTracingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkTracerCreationOtelJava() {
        val fixture = OtelJavaTracerCreationFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkSpanCreationOtelJava() {
        val fixture = OtelJavaSpanCreationFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkSpanEndOtelJava() {
        val fixture = OtelJavaSpanEndFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkComplexSpanCreationOtelJava() {
        val fixture = OtelJavaComplexSpanCreationFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkAddSpanEventOtelJava() {
        val fixture = OtelJavaAddSpanEventFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkAddSpanAttributeOtelJava() {
        val fixture = OtelJavaAddSpanAttributeFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkAddSpanLinkOtelJava() {
        val fixture = OtelJavaAddSpanLinkFixture(createOtelJavaOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }
}
