package io.embrace.opentelemetry.kotlin

/**
 * Constructs an [OpenTelemetry] instance that uses the opentelemetry-kotlin implementation.
 */
@ExperimentalApi
public fun createOpenTelemetry(): OpenTelemetry = createOpenTelemetryImpl()
