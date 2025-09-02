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
public fun createOpenTelemetryInstance(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockImpl(),
): OpenTelemetry {
    return createOpenTelemetryInstanceImpl(
        tracerProvider,
        loggerProvider,
        clock,
    )
}

/**
 * Internal implementation of [createOpenTelemetryInstance]. This is not publicly visible as
 * we don't want to allow users to supply a custom [SdkFactory].
 */
@ExperimentalApi
internal fun createOpenTelemetryInstanceImpl(
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
