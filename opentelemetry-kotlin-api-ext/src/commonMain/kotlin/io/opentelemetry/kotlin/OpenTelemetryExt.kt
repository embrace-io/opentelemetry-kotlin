package io.opentelemetry.kotlin

import io.opentelemetry.kotlin.logging.Logger
import io.opentelemetry.kotlin.tracing.Tracer

@ExperimentalApi
@ThreadSafe
public fun OpenTelemetry.getTracer(instrumentationScopeName: String): Tracer {
    return tracerProvider.getTracer(name = instrumentationScopeName)
}

@ExperimentalApi
@ThreadSafe
public fun OpenTelemetry.getLogger(instrumentationScopeName: String): Logger {
    return loggerProvider.getLogger(name = instrumentationScopeName)
}
