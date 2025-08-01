package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProviderAdapter

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
