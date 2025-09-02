package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.clock.ClockAdapter
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.factory.createCompatSdkFactory
import io.embrace.opentelemetry.kotlin.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.TracerProviderAdapter

/**
 * Constructs an [OpenTelemetry] instance that exposes OpenTelemetry as a Kotlin API.
 * Callers must pass a reference to an OpenTelemetry Java SDK instance. Under the hood calls to the
 * Kotlin API will be delegated to the Java SDK implementation.
 *
 * This function is useful if you have existing OpenTelemetry Java SDK code that you don't want
 * to/can't rewrite, but still wish to use the Kotlin API for new code. It is permitted to call
 * both the Kotlin and Java APIs throughout the lifecycle of your application, although it would
 * generally be encouraged to migrate to [createOpenTelemetryKotlin] as a long-term goal.
 */
@ExperimentalApi
public fun OtelJavaOpenTelemetry.toOtelKotlinApi(): OpenTelemetry {
    val sdkFactory: SdkFactory = createCompatSdkFactory()
    val clock = ClockAdapter(OtelJavaClock.getDefault())
    return OpenTelemetryImpl(
        tracerProvider = TracerProviderAdapter(tracerProvider, clock),
        loggerProvider = LoggerProviderAdapter(logsBridge),
        clock = clock,
        sdkFactory = sdkFactory
    )
}
