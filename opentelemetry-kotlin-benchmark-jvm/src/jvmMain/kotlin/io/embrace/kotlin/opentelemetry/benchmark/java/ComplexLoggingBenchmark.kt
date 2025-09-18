package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import io.opentelemetry.api.logs.Severity
import java.util.concurrent.TimeUnit
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class ComplexLoggingBenchmark {

    private lateinit var logger: OtelJavaLogger

    @Setup
    fun setup() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        val logger = otel.logsBridge.get("logger")
    }

    @Benchmark
    fun benchmarkComplexLog() {
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
