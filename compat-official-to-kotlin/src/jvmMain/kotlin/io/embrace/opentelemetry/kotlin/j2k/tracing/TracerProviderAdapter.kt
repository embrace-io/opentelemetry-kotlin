package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.TracerProvider

@Suppress("UNUSED_VARIABLE")
internal class TracerProviderAdapter(
    private val tracerProvider: io.embrace.opentelemetry.kotlin.tracing.TracerProvider
) : TracerProvider {

    override fun get(instrumentationScopeName: String): Tracer {
        val tracer = tracerProvider.getTracer(instrumentationScopeName)
        TODO()
    }

    override fun get(instrumentationScopeName: String, instrumentationScopeVersion: String): Tracer {
        val tracer = tracerProvider.getTracer(instrumentationScopeName, instrumentationScopeVersion)
        TODO()
    }
}
