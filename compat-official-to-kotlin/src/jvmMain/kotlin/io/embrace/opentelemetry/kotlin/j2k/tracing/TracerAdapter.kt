package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.tracing.Tracer

@OptIn(ExperimentalApi::class)
internal class TracerAdapter(
    private val tracer: Tracer
) : OtelJavaTracer {
    override fun spanBuilder(spanName: String): OtelJavaSpanBuilder = SpanBuilderAdapter(tracer, spanName)
}
