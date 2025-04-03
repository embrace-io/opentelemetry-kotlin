package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

/**
 * The main entry point for the OpenTelemetry API.
 */
@ExperimentalApi
public interface OpenTelemetry {

    /**
     * The [TracerProvider] for creating [Tracer] instances.
     */
    public val tracerProvider: TracerProvider

    /**
     * The [LoggerProvider] for creating [Logger] instances.
     */
    public val loggerProvider: LoggerProvider
}
