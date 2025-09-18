package io.embrace.kotlin.opentelemetry.benchmark

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.createCompatOpenTelemetry
import io.embrace.opentelemetry.kotlin.logging.Logger
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@OptIn(ExperimentalApi::class)
@State(Scope.Benchmark)
class LoggingBenchmark {

    private lateinit var logger: Logger

    @Setup
    fun setup() {
        val otel = createCompatOpenTelemetry()
        logger = otel.loggerProvider.getLogger("logger")
    }

    @Benchmark
    fun benchmarkSimpleLog() {
        logger.log("Hello world!")
    }
}
