package io.embrace.opentelemetry.kotlin.k2j

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.OpenTelemetry
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaOpenTelemetry
import io.embrace.opentelemetry.kotlin.k2j.logging.LoggerProviderAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.TracerProviderAdapter
import io.embrace.opentelemetry.kotlin.logging.LoggerProvider
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
public class OpenTelemetrySdk(
    private val impl: OtelJavaOpenTelemetry
) : OpenTelemetry {

    override val tracerProvider: TracerProvider by lazy { TracerProviderAdapter(impl.tracerProvider) }
    override val loggerProvider: LoggerProvider by lazy { LoggerProviderAdapter(impl.logsBridge) }
}
