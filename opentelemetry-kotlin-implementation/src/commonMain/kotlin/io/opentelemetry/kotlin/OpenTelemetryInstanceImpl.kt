package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.clock.ClockImpl
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.creator.createObjectCreator
import io.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.opentelemetry.kotlin.init.LoggerProviderConfigImpl
import io.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.opentelemetry.kotlin.init.TracerProviderConfigImpl
import io.opentelemetry.kotlin.logging.LoggerProviderImpl
import io.opentelemetry.kotlin.tracing.TracerProviderImpl

/**
 * Constructs an [OpenTelemetry] instance that uses the opentelemetry-kotlin implementation.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.default(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockImpl(),
    objectCreator: ObjectCreator = createObjectCreator()
): OpenTelemetry {
    val tracingConfig = TracerProviderConfigImpl().apply(tracerProvider).generateTracingConfig()
    val loggingConfig = LoggerProviderConfigImpl().apply(loggerProvider).generateLoggingConfig()
    val sdkErrorHandler = NoopSdkErrorHandler
    return OpenTelemetryImpl(
        tracerProvider = TracerProviderImpl(clock, tracingConfig, objectCreator, sdkErrorHandler),
        loggerProvider = LoggerProviderImpl(clock, loggingConfig, objectCreator, sdkErrorHandler),
        clock = clock,
        objectCreator = objectCreator
    )
}
