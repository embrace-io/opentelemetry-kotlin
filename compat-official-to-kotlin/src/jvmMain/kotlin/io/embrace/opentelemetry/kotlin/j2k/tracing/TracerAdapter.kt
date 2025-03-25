package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.opentelemetry.api.trace.SpanBuilder
import io.opentelemetry.api.trace.Tracer

internal class TracerAdapter(
    private val tracer: io.embrace.opentelemetry.kotlin.tracing.Tracer
) : Tracer {
    override fun spanBuilder(spanName: String): SpanBuilder = SpanBuilderAdapter(tracer, spanName)
}
