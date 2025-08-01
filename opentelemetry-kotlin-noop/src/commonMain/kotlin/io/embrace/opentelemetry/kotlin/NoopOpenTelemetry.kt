package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.creator.NoopObjectCreator
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.NoopLoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.NoopTracerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
internal object NoopOpenTelemetry : OpenTelemetry {
    override val tracerProvider: TracerProvider = NoopTracerProvider
    override val loggerProvider: LoggerProvider = NoopLoggerProvider
    override val clock: Clock = NoopClock
    override val objectCreator: ObjectCreator = NoopObjectCreator
}
