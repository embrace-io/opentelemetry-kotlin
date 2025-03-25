package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.SpanRelationshipContainer
import io.embrace.opentelemetry.kotlin.tracing.Tracer

internal class TracerAdapter(private val tracer: io.opentelemetry.api.trace.Tracer) : Tracer {
    override fun createSpan(
        name: String,
        parent: SpanContext?,
        spanKind: SpanKind,
        startTimestamp: Long,
        action: SpanRelationshipContainer.() -> Unit
    ): Span {
        val builder = tracer.spanBuilder(name)
        // TODO: populate other fields
        val span = builder.startSpan()
        return SpanAdapter(span)
    }
}
