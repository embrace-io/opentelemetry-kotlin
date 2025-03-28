package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.TracerProvider

@OptIn(ExperimentalApi::class)
internal class TracerProviderAdapter(
    private val tracerProvider: io.embrace.opentelemetry.kotlin.tracing.TracerProvider
) : TracerProvider {

    override fun get(instrumentationScopeName: String): Tracer {
        val tracer = tracerProvider.getTracer(instrumentationScopeName)
        return TracerAdapter(tracer)
    }

    override fun get(instrumentationScopeName: String, instrumentationScopeVersion: String): Tracer {
        val tracer = tracerProvider.getTracer(instrumentationScopeName, instrumentationScopeVersion)
        return TracerAdapter(tracer)
    }
}
