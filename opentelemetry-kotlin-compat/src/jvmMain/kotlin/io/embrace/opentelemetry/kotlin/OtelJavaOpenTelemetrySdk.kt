package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContextPropagators
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLoggerProvider
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracerProvider
import io.opentelemetry.api.logs.LoggerProvider

internal class OtelJavaOpenTelemetrySdk(
    private val tracerProvider: OtelJavaTracerProvider,
    private val loggerProvider: OtelJavaLoggerProvider,
) : OtelJavaOpenTelemetry {

    override fun getTracerProvider(): OtelJavaTracerProvider = tracerProvider
    override fun getLogsBridge(): LoggerProvider = loggerProvider
    override fun getPropagators(): OtelJavaContextPropagators = OtelJavaContextPropagators.noop()
}
