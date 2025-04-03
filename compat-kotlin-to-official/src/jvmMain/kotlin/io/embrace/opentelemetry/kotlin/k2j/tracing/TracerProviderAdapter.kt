package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.TracerProvider

@ExperimentalApi
public class TracerProviderAdapter(
    private val tracerProvider: OtelJavaTracerProvider,
    private val clock: ClockAdapter = ClockAdapter()
) : TracerProvider {

    override fun getTracer(name: String, version: String?): Tracer {
        val tracer = when (version) {
            null -> tracerProvider.get(name)
            else -> tracerProvider.get(name, version)
        }
        return TracerAdapter(tracer, clock)
    }
}
