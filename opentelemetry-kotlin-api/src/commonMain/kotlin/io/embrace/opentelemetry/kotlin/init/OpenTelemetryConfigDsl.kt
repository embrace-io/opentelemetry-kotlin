package io.embrace.opentelemetry.kotlin.init

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi

/**
 * Defines configuration for [io.embrace.opentelemetry.kotlin.OpenTelemetry].
 */
@ExperimentalApi
@ConfigDsl
public interface OpenTelemetryConfigDsl {

    /**
     * Defines configuration for the [io.embrace.opentelemetry.kotlin.tracing.TracerProvider].
     */
    public fun tracerProvider(action: TracerProviderConfigDsl.() -> Unit)

    /**
     * Defines configuration for the [io.embrace.opentelemetry.kotlin.logging.LoggerProvider].
     */
    public fun loggerProvider(action: LoggerProviderConfigDsl.() -> Unit)

    /**
     * Defines the [Clock] implementation used by OpenTelemetry.
     */
    public var clock: Clock
}
