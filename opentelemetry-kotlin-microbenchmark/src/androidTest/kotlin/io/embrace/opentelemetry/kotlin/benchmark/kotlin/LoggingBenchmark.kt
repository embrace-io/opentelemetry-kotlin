package io.embrace.opentelemetry.kotlin.benchmark.kotlin

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createOpenTelemetry
import io.embrace.opentelemetry.kotlin.logging.model.SeverityNumber
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class LoggingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkLoggerCreation() {
        val otel = createOpenTelemetry()
        benchmarkRule.measureRepeated {
            otel.loggerProvider.getLogger(
                "test",
                "0.1.0",
                "https://example.com/schema"
            ) {
                setStringAttribute("key", "value")
            }
        }
    }

    @Test
    fun benchmarkSimpleLog() {
        val otel = createOpenTelemetry()
        val logger = otel.loggerProvider.getLogger("logger")
        benchmarkRule.measureRepeated {
            logger.log("Hello world!")
        }
    }

    @Test
    fun benchmarkComplexLog() {
        val otel = createOpenTelemetry()
        val logger = otel.loggerProvider.getLogger("logger")
        benchmarkRule.measureRepeated {
            logger.log(
                "Hello world!",
                500,
                1000,
                otel.contextFactory.root(),
                SeverityNumber.DEBUG3,
                "debug3"
            ) {
                setStringAttribute("key", "value")
            }
        }
    }
}
