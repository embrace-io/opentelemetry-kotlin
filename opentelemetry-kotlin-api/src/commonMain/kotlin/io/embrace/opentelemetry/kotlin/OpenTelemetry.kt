package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.factory.SdkFactory
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

    /**
     * The [Clock] that will be used for obtaining timestamps by this instance.
     */
    public val clock: Clock

    /**
     * Used for creating new objects that can be passed to the SDK.
     */
    public val sdkFactory: SdkFactory
}
