package io.embrace.opentelemetry.kotlin.tracing

import io.opentelemetry.api.trace.SpanBuilder

internal class TracerBindingJ2K(
    private val tracer: Tracer
) : io.opentelemetry.api.trace.Tracer {

    override fun spanBuilder(spanName: String): SpanBuilder {
        TODO()
    }
}
