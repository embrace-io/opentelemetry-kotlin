package io.embrace.opentelemetry.kotlin.benchmark.java

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.opentelemetry.api.logs.Severity
import java.util.concurrent.TimeUnit
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalApi::class)
@RunWith(AndroidJUnit4::class)
class OtelJavaLoggingBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkLoggerCreation() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        benchmarkRule.measureRepeated {
            // note: not possible to pass version/schemaUrl/attributes in opentelemetry-java
            otel.logsBridge.get("test")
        }
    }

    @Test
    fun benchmarkSimpleLog() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val logger = otel.logsBridge.get("logger")
        benchmarkRule.measureRepeated {
            logger.logRecordBuilder().setBody("Hello world!").emit()
        }
    }

    @Test
    fun benchmarkComplexLog() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val logger = otel.logsBridge.get("logger")
        benchmarkRule.measureRepeated {
            logger.logRecordBuilder()
                .setBody("Hello world!")
                .setTimestamp(500, TimeUnit.NANOSECONDS)
                .setObservedTimestamp(1000, TimeUnit.NANOSECONDS)
                .setContext(OtelJavaContext.root())
                .setSeverity(Severity.DEBUG3)
                .setSeverityText("debug3")
                .setAttribute("key", "value")
                .emit()
        }
    }
}
