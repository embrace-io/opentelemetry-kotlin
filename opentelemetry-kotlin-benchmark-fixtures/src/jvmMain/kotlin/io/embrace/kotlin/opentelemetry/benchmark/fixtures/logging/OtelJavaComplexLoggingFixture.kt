package io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLogger
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSeverity
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
class OtelJavaComplexLoggingFixture(
    otel: OtelJavaOpenTelemetry
) : BenchmarkFixture {

    private val logger: OtelJavaLogger = otel.logsBridge.get("logger")

    override fun execute() {
        logger.logRecordBuilder()
            .setBody("Hello world!")
            .setTimestamp(500, TimeUnit.NANOSECONDS)
            .setObservedTimestamp(1000, TimeUnit.NANOSECONDS)
            .setContext(OtelJavaContext.root())
            .setSeverity(OtelJavaSeverity.DEBUG3)
            .setSeverityText("debug3")
            .setAttribute("key", "value")
            .emit()
    }
}
