package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.k2j.OpenTelemetrySdk

/**
 * Constructs an [OpenTelemetry] instance that decorates the OpenTelemetry Java SDK. This will not use the Kotlin
 * implementation under the hood and will solely use the Java SDK implementation. This is useful if you have existing
 * OpenTelemetry Java SDK code that you don't want to rewrite, but still wish to use the Kotlin API.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.compatWithOtelJava(impl: OtelJavaOpenTelemetry): OpenTelemetry = OpenTelemetrySdk(impl)

/**
 * Constructs an [OpenTelemetry] instance by using the Kotlin API DSL to configure an underlying OpenTelemetry Java SDK
 * instance.
 */
@Suppress("UNUSED_PARAMETER")
@ExperimentalApi
public fun OpenTelemetryInstance.kotlinApi(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockAdapter(io.opentelemetry.sdk.common.Clock.getDefault()),
): OpenTelemetry = throw UnsupportedOperationException()
