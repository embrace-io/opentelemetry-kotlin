package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.factory.createSdkFactory
import io.embrace.opentelemetry.kotlin.init.OpenTelemetryConfigDsl
import io.embrace.opentelemetry.kotlin.init.OpenTelemetryConfigImpl
import io.embrace.opentelemetry.kotlin.logging.LoggerProviderImpl
import io.embrace.opentelemetry.kotlin.tracing.TracerProviderImpl

/**
 * Constructs an [OpenTelemetry] instance that uses the opentelemetry-kotlin implementation.
 */
@ExperimentalApi
public fun createOpenTelemetry(config: OpenTelemetryConfigDsl.() -> Unit = {}): OpenTelemetry {
    return createOpenTelemetryImpl(
        config,
        createSdkFactory(),
    )
}

/**
 * Internal implementation of [createOpenTelemetry]. This is not publicly visible as
 * we don't want to allow users to supply a custom [SdkFactory].
 */
@ExperimentalApi
internal fun createOpenTelemetryImpl(
    config: OpenTelemetryConfigDsl.() -> Unit,
    sdkFactory: SdkFactory,
): OpenTelemetry {
    val cfg = OpenTelemetryConfigImpl().apply(config)
    val tracingConfig = cfg.tracingConfig.generateTracingConfig()
    val loggingConfig = cfg.loggingConfig.generateLoggingConfig()
    val clock = cfg.clock
    return OpenTelemetryImpl(
        tracerProvider = TracerProviderImpl(clock, tracingConfig, sdkFactory),
        loggerProvider = LoggerProviderImpl(clock, loggingConfig, sdkFactory),
        clock = clock,
        sdkFactory = sdkFactory
    )
}
