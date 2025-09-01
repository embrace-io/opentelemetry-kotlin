package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.clock.ClockImpl
import io.embrace.opentelemetry.kotlin.error.NoopSdkErrorHandler
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.factory.createSdkFactory
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
    sdkFactory: SdkFactory = createSdkFactory()
): OpenTelemetry {
    val tracingConfig = TracerProviderConfigImpl().apply(tracerProvider).generateTracingConfig()
    val loggingConfig = LoggerProviderConfigImpl().apply(loggerProvider).generateLoggingConfig()
    val sdkErrorHandler = NoopSdkErrorHandler
    return OpenTelemetryImpl(
        tracerProvider = TracerProviderImpl(clock, tracingConfig, sdkFactory, sdkErrorHandler),
        loggerProvider = LoggerProviderImpl(clock, loggingConfig, sdkFactory, sdkErrorHandler),
        clock = clock,
        sdkFactory = sdkFactory
    )
}
