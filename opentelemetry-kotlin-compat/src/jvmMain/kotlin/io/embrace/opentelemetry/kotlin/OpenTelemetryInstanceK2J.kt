package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.k2j.init.LoggerProviderConfigImpl
import io.embrace.opentelemetry.kotlin.k2j.init.TracerProviderConfigImpl
import io.embrace.opentelemetry.kotlin.k2j.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter

/**
 * Constructs an [OpenTelemetry] instance that decorates the OpenTelemetry Java SDK. This will not use the Kotlin
 * implementation under the hood and will solely use the Java SDK implementation. This is useful if you have existing
 * OpenTelemetry Java SDK code that you don't want to rewrite, but still wish to use the Kotlin API.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.compatWithOtelJava(impl: OtelJavaOpenTelemetry): OpenTelemetry =
    OpenTelemetrySdk(
        tracerProvider = TracerProviderAdapter(impl.tracerProvider),
        loggerProvider = LoggerProviderAdapter(impl.logsBridge),
        clock = ClockAdapter(OtelJavaClock.getDefault())
    )

/**
 * Constructs an [OpenTelemetry] instance by using the Kotlin API DSL to configure an underlying OpenTelemetry Java SDK
 * instance.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.kotlinApi(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockAdapter(io.opentelemetry.sdk.common.Clock.getDefault()),
): OpenTelemetry {
    return OpenTelemetrySdk(
        tracerProvider = TracerProviderConfigImpl(clock).apply(tracerProvider).build(),
        loggerProvider = LoggerProviderConfigImpl(clock).apply(loggerProvider).build(),
        clock = clock
    )
}
