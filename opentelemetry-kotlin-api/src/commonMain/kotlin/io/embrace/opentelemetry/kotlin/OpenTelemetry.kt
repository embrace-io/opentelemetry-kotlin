package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

/**
 * The main entry point for the OpenTelemetry API.
 */
@ExperimentalApi
public interface OpenTelemetry : SdkFactory {

    /**
     * The [io.embrace.opentelemetry.kotlin.tracing.TracerProvider] for creating [io.embrace.opentelemetry.kotlin.tracing.Tracer] instances.
     */
    public val tracerProvider: TracerProvider

    /**
     * The [io.embrace.opentelemetry.kotlin.logging.LoggerProvider] for creating [io.embrace.opentelemetry.kotlin.logging.Logger] instances.
     */
    public val loggerProvider: LoggerProvider

    /**
     * The [Clock] that will be used for obtaining timestamps by this instance.
     */
    public val clock: Clock
}
