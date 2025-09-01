package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.clock.FakeClock
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.logging.FakeLoggerProvider
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.FakeTracerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@OptIn(ExperimentalApi::class)
class FakeOpenTelemetry : OpenTelemetry {
    override val tracerProvider: TracerProvider = FakeTracerProvider()
    override val loggerProvider: LoggerProvider = FakeLoggerProvider()
    override val clock: Clock = FakeClock()
    override val sdkFactory: SdkFactory
        get() = throw UnsupportedOperationException()
}
