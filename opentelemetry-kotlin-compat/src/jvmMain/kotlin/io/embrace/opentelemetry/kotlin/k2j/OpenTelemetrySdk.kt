package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaClock
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.k2j.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
internal class OpenTelemetrySdk(
    override val tracerProvider: TracerProvider,
    override val loggerProvider: LoggerProvider,
    override val clock: Clock,
) : OpenTelemetry {

    constructor(impl: OtelJavaOpenTelemetry) : this(
        TracerProviderAdapter(impl.tracerProvider),
        LoggerProviderAdapter(impl.logsBridge),
        ClockAdapter(OtelJavaClock.getDefault())
    )
}
