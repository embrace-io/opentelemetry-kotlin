package io.embrace.opentelemetry.kotlin.benchmark.kotlin

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import io.embrace.opentelemetry.kotlin.createNoopOpenTelemetry
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class EntrypointBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkNoopEntrypoint() {
        benchmarkRule.measureRepeated {
            createNoopOpenTelemetry()
        }
    }

    @Test
    fun benchmarkImplementationEntrypoint() {
        benchmarkRule.measureRepeated {
            createOpenTelemetry()
        }
    }

    @Test
    fun benchmarkCompatEntrypoint() {
        benchmarkRule.measureRepeated {
            createCompatOpenTelemetry()
        }
    }
}
