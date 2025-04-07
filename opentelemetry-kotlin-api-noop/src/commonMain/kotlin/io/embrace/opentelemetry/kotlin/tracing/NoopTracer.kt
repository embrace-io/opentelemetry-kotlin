package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
internal object NoopTracer : Tracer {
    override fun createSpan(
        name: String,
        parent: Span?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationshipContainer.() -> Unit
    ): Span = NoopSpan
}
