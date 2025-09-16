package io.embrace.opentelemetry.kotlin.benchmark.java

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class OtelJavaEntrypointBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkNoopEntrypoint() {
        benchmarkRule.measureRepeated {
            OtelJavaOpenTelemetry.noop()
        }
    }

    @Test
    fun benchmarkImplementationEntrypoint() {
        benchmarkRule.measureRepeated {
            OtelJavaOpenTelemetrySdk.builder().build()
        }
    }
}