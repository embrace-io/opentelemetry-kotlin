package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.clock.ClockAdapter
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.factory.createCompatSdkFactory
import io.embrace.opentelemetry.kotlin.init.CompatLoggerProviderConfig
import io.embrace.opentelemetry.kotlin.init.CompatTracerProviderConfig
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl

/**
 * Constructs an [OpenTelemetry] instance that exposes OpenTelemetry as a Kotlin API. The SDK is
 * configured entirely via the Kotlin DSL. Under the hood all calls to the Kotlin API will be
 * delegated to an OpenTelemetry Java SDK implementation that this SDK will construct internally.
 *
 * It's not possible to obtain a reference to the Java API using this function. If this is a
 * requirement because you have existing instrumentation, it's recommended to call
 * [toOtelKotlinApi] instead.
 */
@ExperimentalApi
public fun createCompatOpenTelemetryInstance(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockAdapter(io.opentelemetry.sdk.common.Clock.getDefault()),
    sdkFactory: SdkFactory = createCompatSdkFactory(),
): OpenTelemetry {
    sdkFactory.tracingIdFactory
    val tracerCfg = CompatTracerProviderConfig(clock, sdkFactory).apply(tracerProvider)
    val loggerCfg = CompatLoggerProviderConfig(clock).apply(loggerProvider)

    return OpenTelemetryImpl(
        tracerProvider = tracerCfg.build(),
        loggerProvider = loggerCfg.build(),
        clock = clock,
        sdkFactory = sdkFactory,
    )
}
