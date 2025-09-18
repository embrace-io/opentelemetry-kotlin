package io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.logging.Logger

@OptIn(ExperimentalApi::class)
class SimpleLoggingFixture(
    otel: OpenTelemetry
) : BenchmarkFixture {

    private val logger: Logger = otel.loggerProvider.getLogger("logger")

    override fun execute() {
        logger.log("Hello world!")
    }
}
