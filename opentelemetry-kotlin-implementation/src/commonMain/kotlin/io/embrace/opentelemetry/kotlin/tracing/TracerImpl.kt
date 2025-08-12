package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.creator.ObjectCreator
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.CreatedSpan
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanRecord
import io.embrace.opentelemetry.kotlin.tracing.model.SpanRelationships

@Suppress("unused")
@OptIn(ExperimentalApi::class)
internal class TracerImpl(
    private val clock: Clock,
    private val processor: SpanProcessor,
    private val objectCreator: ObjectCreator,
    private val scope: InstrumentationScopeInfo,
    private val resource: Resource,
) : Tracer {

    override fun createSpan(
        name: String,
        parentContext: Context?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationships.() -> Unit
    ): Span {
        val spanRelationships = SpanRelationshipsImpl(clock)
        action(spanRelationships)
        val spanRecord = SpanRecord(
            clock = clock,
            processor = processor,
            parentContext = parentContext ?: objectCreator.context.root(),
            name = name,
            spanRelationships = spanRelationships,
            spanKind = spanKind,
            startTimestamp = startTimestamp ?: clock.now(),
            instrumentationScopeInfo = scope,
            resource = resource,
        )
        return CreatedSpan(spanRecord)
    }
}
