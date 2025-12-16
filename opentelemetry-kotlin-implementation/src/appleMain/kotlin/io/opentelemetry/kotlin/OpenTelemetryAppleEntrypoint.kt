package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.OpenTelemetry

/**
 * Constructs an [io.opentelemetry.kotlin.OpenTelemetry] instance that uses the opentelemetry-kotlin implementation.
 */
@ExperimentalApi
public fun createAppleOpenTelemetry(): OpenTelemetry = createOpenTelemetry()
