package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.clock.ClockAdapter
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.creator.createCompatObjectCreator
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.LoggerProviderConfigImpl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigDsl
import io.embrace.opentelemetry.kotlin.init.TracerProviderConfigImpl
import io.embrace.opentelemetry.kotlin.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.logging.OtelJavaLoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.OtelJavaTracerProviderAdapter
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
public fun OpenTelemetryInstance.decorateJavaApi(
    impl: OtelJavaOpenTelemetry,
    objectCreator: ObjectCreator = createCompatObjectCreator(),
): OpenTelemetry {
    val clock = ClockAdapter(OtelJavaClock.getDefault())
    return OpenTelemetryImpl(
        tracerProvider = TracerProviderAdapter(impl.tracerProvider, clock),
        loggerProvider = LoggerProviderAdapter(impl.logsBridge),
        clock = clock,
        objectCreator = objectCreator
    )
}

/**
 * Constructs an [OpenTelemetry] instance that exposes OpenTelemetry as a Kotlin API. The SDK is
 * configured entirely via the Kotlin DSL. Under the hood all calls to the Kotlin API will be
 * delegated to an OpenTelemetry Java SDK implementation that this SDK will construct internally.
 *
 * It's not possible to obtain a reference to the Java API using this function. If this is a
 * requirement because you have existing instrumentation, it's recommended to call
 * [decorateJavaApi] instead.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.createOpenTelemetryKotlin(
    tracerProvider: TracerProviderConfigDsl.() -> Unit = {},
    loggerProvider: LoggerProviderConfigDsl.() -> Unit = {},
    clock: Clock = ClockAdapter(io.opentelemetry.sdk.common.Clock.getDefault()),
    objectCreator: ObjectCreator = createCompatObjectCreator(),
): OpenTelemetry {
    objectCreator.idCreator
    val tracerCfg = TracerProviderConfigImpl(clock, objectCreator).apply(tracerProvider)
    val loggerCfg = LoggerProviderConfigImpl(clock).apply(loggerProvider)

    return OpenTelemetryImpl(
        tracerProvider = tracerCfg.build(),
        loggerProvider = loggerCfg.build(),
        clock = clock,
        objectCreator = objectCreator,
    )
}

/**
 * Constructs an [OtelJavaOpenTelemetry] instance that makes the Kotlin implementation conform
 * to the opentelemetry-java API.
 *
 * End-users should generally not use this function and should call [createOpenTelemetryKotlin]
 * or [decorateJavaApi] instead.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.decorateKotlinApi(impl: OpenTelemetry): OtelJavaOpenTelemetry {
    return OtelJavaOpenTelemetrySdk(
        tracerProvider = OtelJavaTracerProviderAdapter(impl.tracerProvider),
        loggerProvider = OtelJavaLoggerProviderAdapter(impl.loggerProvider)
    )
}
