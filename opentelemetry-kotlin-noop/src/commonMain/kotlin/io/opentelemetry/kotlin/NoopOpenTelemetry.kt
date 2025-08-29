package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.creator.NoopObjectCreator
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.logging.LoggerProvider
import io.opentelemetry.kotlin.logging.NoopLoggerProvider
import io.opentelemetry.kotlin.tracing.NoopTracerProvider
import io.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
internal object NoopOpenTelemetry : OpenTelemetry {
    override val tracerProvider: TracerProvider = NoopTracerProvider
    override val loggerProvider: LoggerProvider = NoopLoggerProvider
    override val clock: Clock = NoopClock
    override val objectCreator: ObjectCreator = NoopObjectCreator
}
