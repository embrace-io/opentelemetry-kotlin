package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.Clock
import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.aliases.OtelJavaContext
import io.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.opentelemetry.kotlin.context.Context
import io.opentelemetry.kotlin.context.OtelJavaContextAdapter
import io.opentelemetry.kotlin.context.toOtelJavaContext
import io.opentelemetry.kotlin.tracing.ext.toOtelJavaSpanKind
import io.opentelemetry.kotlin.tracing.model.Span
import io.opentelemetry.kotlin.tracing.model.SpanAdapter
import io.opentelemetry.kotlin.tracing.model.SpanKind
import io.opentelemetry.kotlin.tracing.model.SpanRelationships
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class TracerAdapter(
    private val tracer: OtelJavaTracer,
    private val clock: Clock
) : Tracer {

    override fun createSpan(
        name: String,
        parentContext: Context?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationships.() -> Unit
    ): Span {
        val start = startTimestamp ?: clock.now()
        val builder = tracer.spanBuilder(name)
            .setSpanKind(spanKind.toOtelJavaSpanKind())
            .setStartTimestamp(start, TimeUnit.NANOSECONDS)

        if (parentContext != null) {
            builder.setParent(parentContext.toOtelJavaContext())
        } else {
            builder.setNoParent()
        }

        val span = builder.startSpan()
        return SpanAdapter(
            impl = span,
            clock = clock,
            parentCtx = parentContext?.let(::OtelJavaContextAdapter) ?: OtelJavaContext.current()
                ?: OtelJavaContext.root(),
            spanKind = spanKind,
            startTimestamp = start
        ).apply {
            this.name = name
            action(this)
        }
    }
}
