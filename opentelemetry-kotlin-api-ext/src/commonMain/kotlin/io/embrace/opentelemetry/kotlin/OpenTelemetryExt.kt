package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.logging.Logger
import io.embrace.opentelemetry.kotlin.tracing.Tracer

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
