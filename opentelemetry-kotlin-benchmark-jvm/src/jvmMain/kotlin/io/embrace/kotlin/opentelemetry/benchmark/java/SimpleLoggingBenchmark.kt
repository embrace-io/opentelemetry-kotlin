package io.embrace.kotlin.opentelemetry.benchmark.java

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetrySdk
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class SimpleLoggingBenchmark {

    private lateinit var logger: OtelJavaLogger

    @Setup
    fun setup() {
        val otel = OtelJavaOpenTelemetrySdk.builder().build()
        logger = otel.logsBridge.get("logger")
    }

    @Benchmark
    fun benchmarkSimpleLog() {
        logger.logRecordBuilder().setBody("Hello world!").emit()
    }
}
