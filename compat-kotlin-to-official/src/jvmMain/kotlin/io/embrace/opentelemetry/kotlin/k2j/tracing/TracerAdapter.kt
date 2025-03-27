package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.SpanRelationshipContainer
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import java.util.concurrent.TimeUnit

internal class TracerAdapter(private val tracer: io.opentelemetry.api.trace.Tracer) : Tracer {
    override fun createSpan(
        name: String,
        parent: SpanContext?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationshipContainer.() -> Unit
    ): Span {
        val builder = tracer.spanBuilder(name)
            .setSpanKind(spanKind.convertToOtelJava())
            .setStartTimestamp(startTimestamp ?: 0, TimeUnit.NANOSECONDS) // TODO: use clock if not provided

        val span = builder.startSpan()
        return SpanAdapter(span).apply {
            this.name = name
            action(this)
            // TODO: populate other fields
        }
    }
}
