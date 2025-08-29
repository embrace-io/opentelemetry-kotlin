package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.clock.FakeClock
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.logging.FakeLoggerProvider
import io.opentelemetry.kotlin.logging.LoggerProvider
import io.opentelemetry.kotlin.tracing.FakeTracerProvider
import io.opentelemetry.kotlin.tracing.TracerProvider

@OptIn(ExperimentalApi::class)
class FakeOpenTelemetry : OpenTelemetry {
    override val tracerProvider: TracerProvider = FakeTracerProvider()
    override val loggerProvider: LoggerProvider = FakeLoggerProvider()
    override val clock: Clock = FakeClock()
    override val objectCreator: ObjectCreator
        get() = throw UnsupportedOperationException()
}
