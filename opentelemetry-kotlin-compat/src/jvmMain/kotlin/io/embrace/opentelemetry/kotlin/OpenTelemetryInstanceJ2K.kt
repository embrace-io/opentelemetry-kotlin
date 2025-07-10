package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.j2k.OtelJavaOpenTelemetrySdk
import io.embrace.opentelemetry.kotlin.j2k.logging.OtelJavaLoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.j2k.tracing.OtelJavaTracerProviderAdapter

/**
 * Constructs an [OtelJavaOpenTelemetry] instance that decorates the OpenTelemetry Kotlin SDK.
 * This will use the Java API to access the Kotlin implementation under the hood, which can be helpful
 * if you have instrumentation already written against the Java API.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.compatWithOtelKotlin(impl: OpenTelemetry): OtelJavaOpenTelemetry {
    return OtelJavaOpenTelemetrySdk(
        tracerProvider = OtelJavaTracerProviderAdapter(impl.tracerProvider),
        loggerProvider = OtelJavaLoggerProviderAdapter(impl.loggerProvider)
    )
}
