package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.factory.SdkFactory
import io.embrace.opentelemetry.kotlin.init.config.SpanLimitConfig
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor
import io.embrace.opentelemetry.kotlin.tracing.model.CreatedSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpanImpl
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanModel
import io.embrace.opentelemetry.kotlin.tracing.model.SpanRelationships

@OptIn(ExperimentalApi::class)
internal class TracerImpl(
    private val clock: Clock,
    private val processor: SpanProcessor,
    private val sdkFactory: SdkFactory,
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
        val ctx = parentContext ?: sdkFactory.contextFactory.root()
        val parentSpanContext = retrieveParentSpanContext(ctx)
        val spanContext = calculateSpanContext(parentSpanContext, sdkFactory)

        val spanModel = SpanModel(
            clock = clock,
            processor = processor,
            name = name,
            spanKind = spanKind,
            startTimestamp = startTimestamp ?: clock.now(),
            instrumentationScopeInfo = scope,
            resource = resource,
            parent = parentSpanContext,
            spanContext = spanContext,
            spanLimitConfig = spanLimitConfig
        )
        action(spanModel)
        processor.onStart(ReadWriteSpanImpl(spanModel), ctx)
        return CreatedSpan(spanModel)
    }

    private fun retrieveParentSpanContext(parent: Context): SpanContext {
        val parentSpan = sdkFactory.spanFactory.fromContext(parent)
        return parentSpan.spanContext
    }

    private fun calculateSpanContext(
        parent: SpanContext,
        sdkFactory: SdkFactory
    ): SpanContext {
        val factory = sdkFactory.tracingIdFactory

        val traceId = if (parent.isValid) {
            parent.traceId
        } else {
            factory.generateTraceId()
        }
        val spanId = factory.generateSpanId()

        return SpanContextImpl(
            traceId = traceId,
            spanId = spanId,
            traceFlags = sdkFactory.traceFlagsFactory.default,
            isValid = true,
            isRemote = false,
            traceState = sdkFactory.traceStateFactory.default,
        )
    }
}
