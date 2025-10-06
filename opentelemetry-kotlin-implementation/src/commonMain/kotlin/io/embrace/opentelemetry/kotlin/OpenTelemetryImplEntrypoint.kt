package io.embrace.opentelemetry.kotlin

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
public fun createOpenTelemetry(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockImpl(),
): OpenTelemetry {
    return createOpenTelemetryImpl(
        tracerProvider,
        loggerProvider,
        clock,
        createSdkFactory(),
    )
}

/**
 * Internal implementation of [createOpenTelemetry]. This is not publicly visible as
 * we don't want to allow users to supply a custom [SdkFactory].
 */
@ExperimentalApi
internal fun createOpenTelemetryImpl(
    tracerProvider: TracerProviderConfigDsl.() -> Unit,
    loggerProvider: LoggerProviderConfigDsl.() -> Unit,
    clock: Clock,
    sdkFactory: SdkFactory,
): OpenTelemetry {
    val tracingConfig = TracerProviderConfigImpl().apply(tracerProvider).generateTracingConfig()
    val loggingConfig = LoggerProviderConfigImpl().apply(loggerProvider).generateLoggingConfig()
    return OpenTelemetryImpl(
        tracerProvider = TracerProviderImpl(clock, tracingConfig, sdkFactory),
        loggerProvider = LoggerProviderImpl(clock, loggingConfig, sdkFactory),
        clock = clock,
        sdkFactory = sdkFactory
    )
}
