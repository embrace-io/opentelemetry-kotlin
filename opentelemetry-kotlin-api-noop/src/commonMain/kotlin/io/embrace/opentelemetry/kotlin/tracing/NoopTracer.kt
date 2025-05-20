package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.context.Context

@ExperimentalApi
internal object NoopTracer : Tracer {
    override fun createSpan(
        name: String,
        parent: SpanContext?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        context: Context?,
        action: SpanRelationships.() -> Unit
    ): Span = NoopSpan
}
