package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.logging.LoggerProvider
import io.opentelemetry.kotlin.tracing.TracerProvider

@OptIn(ExperimentalApi::class)
class OpenTelemetryImpl(
    override val tracerProvider: TracerProvider,
    override val loggerProvider: LoggerProvider,
    override val clock: Clock,
    override val objectCreator: ObjectCreator
) : OpenTelemetry
