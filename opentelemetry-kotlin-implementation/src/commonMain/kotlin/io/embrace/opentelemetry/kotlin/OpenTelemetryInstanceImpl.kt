package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.clock.ClockImpl
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.creator.createObjectCreator
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigImpl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigImpl
import io.embrace.opentelemetry.kotlin.logging.LoggerProviderImpl
import io.embrace.opentelemetry.kotlin.tracing.TracerProviderImpl

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
