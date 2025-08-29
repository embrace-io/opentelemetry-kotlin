package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry

/**
 * Constructs an [OtelJavaOpenTelemetry] instance that makes the Kotlin implementation conform
 * to the opentelemetry-java API.
 *
 * End-users should generally not use this function and should call [createOpenTelemetryKotlin]
 * or [decorateJavaApi] instead.
 */
@ExperimentalApi
public fun OpenTelemetry.toOtelJavaApi(): OtelJavaOpenTelemetry {
    return OtelJavaOpenTelemetrySdk(this)
}
