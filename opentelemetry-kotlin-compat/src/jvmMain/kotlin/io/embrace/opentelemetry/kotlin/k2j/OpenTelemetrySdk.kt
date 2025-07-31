package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.k2j.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
internal class OpenTelemetrySdk(
    override val tracerProvider: TracerProvider,
    override val loggerProvider: LoggerProvider,
    override val clock: Clock,
    override val objectCreator: ObjectCreator,
) : OpenTelemetry {

    constructor(impl: OtelJavaOpenTelemetry, clock: Clock, objectCreator: ObjectCreator) : this(
        TracerProviderAdapter(impl.tracerProvider, clock),
        LoggerProviderAdapter(impl.logsBridge),
        clock,
        objectCreator
    )
}
