package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@OptIn(ExperimentalApi::class)
class OpenTelemetryImpl(
    override val tracerProvider: TracerProvider,
    override val loggerProvider: LoggerProvider,
    override val clock: Clock,
    override val objectCreator: ObjectCreator
) : OpenTelemetry
