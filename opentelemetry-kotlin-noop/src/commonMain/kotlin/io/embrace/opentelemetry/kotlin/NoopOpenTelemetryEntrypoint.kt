package io.embrace.opentelemetry.kotlin

/**
 * Returns a no-op instance of [OpenTelemetry] instance.
 */
@OptIn(ExperimentalApi::class)
public fun noopOpenTelemetry(): OpenTelemetry = NoopOpenTelemetry
