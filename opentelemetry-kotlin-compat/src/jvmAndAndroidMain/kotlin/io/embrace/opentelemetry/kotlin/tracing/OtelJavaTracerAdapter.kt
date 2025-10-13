package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer

@OptIn(ExperimentalApi::class)
internal class OtelJavaTracerAdapter(
    private val tracer: Tracer
) : OtelJavaTracer {
    override fun spanBuilder(spanName: String): OtelJavaSpanBuilder = OtelJavaSpanBuilderAdapter(tracer, spanName)
}
