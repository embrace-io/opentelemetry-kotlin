package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.logging.OtelJavaLoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.OtelJavaTracerProviderAdapter

/**
 * Constructs an [OtelJavaOpenTelemetry] instance that makes the Kotlin implementation conform
 * to the opentelemetry-java API.
 *
 * End-users should generally not use this function and should call [createCompatOpenTelemetryInstance]
 * or [toOtelKotlinApi] instead.
 */
@ExperimentalApi
public fun OpenTelemetry.toOtelJavaApi(): OtelJavaOpenTelemetry {
    return OtelJavaOpenTelemetrySdk(
        OtelJavaTracerProviderAdapter(tracerProvider),
        OtelJavaLoggerProviderAdapter(loggerProvider)
    )
}
