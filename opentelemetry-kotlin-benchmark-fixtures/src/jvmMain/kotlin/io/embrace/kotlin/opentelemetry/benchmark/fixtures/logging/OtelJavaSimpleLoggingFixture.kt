package io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry

@OptIn(ExperimentalApi::class)
class OtelJavaSimpleLoggingFixture(
    otel: OtelJavaOpenTelemetry
) : BenchmarkFixture {

    private val logger: OtelJavaLogger = otel.logsBridge.get("logger")

    override fun execute() {
        logger.logRecordBuilder()
            .setBody("Hello world!")
            .emit()
    }
}
