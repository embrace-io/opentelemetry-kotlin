package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.j2k.OtelKotlinTracer
import io.opentelemetry.api.trace.SpanBuilder
import io.opentelemetry.api.trace.Tracer

@OptIn(ExperimentalApi::class)
internal class TracerAdapter(
    private val tracer: OtelKotlinTracer
) : Tracer {
    override fun spanBuilder(spanName: String): SpanBuilder = SpanBuilderAdapter(tracer, spanName)
}
