package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.clock.ClockImpl

/**
 * Constructs an [OpenTelemetry] instance that uses the opentelemetry-kotlin implementation.
 */
@ExperimentalApi
@Suppress("UNUSED_PARAMETER")
public fun OpenTelemetryInstance.default(
    clock: Clock = ClockImpl()
): OpenTelemetry = throw UnsupportedOperationException()
