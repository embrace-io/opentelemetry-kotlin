package io.embrace.opentelemetry.kotlin

/**
 * Returns a no-op [OpenTelemetry] instance.
 */
@ExperimentalApi
public fun OpenTelemetryInstance.noop(): OpenTelemetry = NoopOpenTelemetry
