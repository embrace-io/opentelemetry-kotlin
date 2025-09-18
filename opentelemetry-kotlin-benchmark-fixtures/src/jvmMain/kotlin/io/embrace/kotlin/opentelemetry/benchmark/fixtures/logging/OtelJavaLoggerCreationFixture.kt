package io.embrace.kotlin.opentelemetry.benchmark.fixtures.logging

import io.embrace.kotlin.opentelemetry.benchmark.fixtures.BenchmarkFixture
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry

@OptIn(ExperimentalApi::class)
class OtelJavaLoggerCreationFixture(
    private val otel: OtelJavaOpenTelemetry
) : BenchmarkFixture {

    override fun execute() {
        // note: not possible to pass version/schemaUrl/attributes in opentelemetry-java
        otel.logsBridge.get("test")
    }
}
