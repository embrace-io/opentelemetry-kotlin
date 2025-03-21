package io.embrace.opentelemetry.kotlin.tracing

import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.TracerProvider

internal class TracerProviderBindingJ2K(
    private val entrypoint: io.embrace.opentelemetry.kotlin.tracing.TracerProvider
) : TracerProvider {

    override fun get(instrumentationScopeName: String): Tracer {
        return TracerBindingJ2K(entrypoint.getTracer(instrumentationScopeName))
    }

    override fun get(instrumentationScopeName: String, instrumentationScopeVersion: String): Tracer {
        return TracerBindingJ2K(entrypoint.getTracer(instrumentationScopeName, instrumentationScopeVersion))
    }
}
