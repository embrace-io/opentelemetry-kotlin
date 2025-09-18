package io.embrace.opentelemetry.kotlin.benchmark.logging

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.ComplexLoggingFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.LoggerCreationFixture
import io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging.SimpleLoggingFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class CompatLoggingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkLoggerCreationCompat() {
        val fixture = LoggerCreationFixture(createCompatOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkSimpleLogCompat() {
        val fixture = SimpleLoggingFixture(createCompatOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }

    @Test
    fun benchmarkComplexLogCompat() {
        val fixture = ComplexLoggingFixture(createCompatOpenTelemetry())
        benchmarkRule.measureRepeated {
            fixture.execute()
        }
    }
}
