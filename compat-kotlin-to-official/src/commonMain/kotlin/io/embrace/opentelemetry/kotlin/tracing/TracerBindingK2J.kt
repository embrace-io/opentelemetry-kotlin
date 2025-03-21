package io.embrace.opentelemetry.kotlin.tracing

internal class TracerBindingK2J(
    private val tracer: io.opentelemetry.api.trace.Tracer
) : Tracer {

    override fun createSpan(
        name: String,
        parent: SpanContext?,
        spanKind: SpanKind,
        startTimestamp: Long,
        action: SpanRelationshipContainer.() -> Unit
    ): Span {
        val builder = tracer.spanBuilder(name)
        return SpanBindingK2J(builder.startSpan())
    }
}
