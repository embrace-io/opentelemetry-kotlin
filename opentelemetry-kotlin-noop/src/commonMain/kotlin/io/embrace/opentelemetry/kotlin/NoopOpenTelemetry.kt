package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.factory.NoopSdkFactory
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.NoopLoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.NoopTracerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
internal object NoopOpenTelemetry : OpenTelemetry, SdkFactory by NoopSdkFactory {
    override val tracerProvider: TracerProvider = NoopTracerProvider
    override val loggerProvider: LoggerProvider = NoopLoggerProvider
    override val clock: Clock = NoopClock
}
