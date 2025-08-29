package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.InstrumentationScopeInfo
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.creator.ObjectCreator
import io.opentelemetry.kotlin.init.config.SpanLimitConfig
import io.opentelemetry.kotlin.resource.Resource
import io.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.opentelemetry.kotlin.tracing.model.CreatedSpan
import io.opentelemetry.kotlin.tracing.model.ReadWriteSpanImpl
import io.opentelemetry.kotlin.tracing.model.Span
import io.opentelemetry.kotlin.tracing.model.SpanContext
import io.opentelemetry.kotlin.tracing.model.SpanKind
import io.opentelemetry.kotlin.tracing.model.SpanModel
import io.opentelemetry.kotlin.tracing.model.SpanRelationships

@OptIn(ExperimentalApi::class)
internal class TracerImpl(
    private val clock: Clock,
    private val processor: SpanProcessor,
    private val objectCreator: ObjectCreator,
    private val scope: InstrumentationScopeInfo,
    private val resource: Resource,
    private val spanLimitConfig: SpanLimitConfig,
) : Tracer {

    override fun createSpan(
        name: String,
        parentContext: Context?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationships.() -> Unit
    ): Span {
        val spanRelationships = SpanRelationshipsImpl(clock, spanLimitConfig)
        action(spanRelationships)

        val ctx = parentContext ?: objectCreator.context.root()
        val parentSpanContext = retrieveParentSpanContext(ctx)
        val spanContext = calculateSpanContext(parentSpanContext, objectCreator)

        val spanModel = SpanModel(
            clock = clock,
            processor = processor,
            name = name,
            spanRelationships = spanRelationships,
            spanKind = spanKind,
            startTimestamp = startTimestamp ?: clock.now(),
            instrumentationScopeInfo = scope,
            resource = resource,
            parent = parentSpanContext,
            spanContext = spanContext,
            spanLimitConfig = spanLimitConfig
        )
        processor.onStart(ReadWriteSpanImpl(spanModel), ctx)
        return CreatedSpan(spanModel)
    }

    private fun retrieveParentSpanContext(parent: Context): SpanContext {
        val parentSpan = objectCreator.span.fromContext(parent)
        return parentSpan.spanContext
    }

    private fun calculateSpanContext(
        parent: SpanContext,
        objectCreator: ObjectCreator
    ): SpanContext {
        val idCreator = objectCreator.idCreator

        val traceId = if (parent.isValid) {
            parent.traceId
        } else {
            idCreator.generateTraceId()
        }
        val spanId = idCreator.generateSpanId()

        return SpanContextImpl(
            traceId = traceId,
            spanId = spanId,
            traceFlags = objectCreator.traceFlags.default,
            isValid = true,
            isRemote = false,
            traceState = objectCreator.traceState.default,
        )
    }
}
