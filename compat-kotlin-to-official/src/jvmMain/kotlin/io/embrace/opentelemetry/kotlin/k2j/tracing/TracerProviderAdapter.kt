package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider
import io.opentelemetry.api.OpenTelemetry

public class TracerProviderAdapter(private val otel: OpenTelemetry) : TracerProvider {
    override fun getTracer(name: String, version: String?): Tracer {
        val tracerProvider = otel.tracerProvider
        val tracer = when (version) {
            null -> tracerProvider.get(name)
            else -> tracerProvider.get(name, version)
        }
        return TracerAdapter(tracer)
    }
}
