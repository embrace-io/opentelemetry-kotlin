package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.context.OtelJavaContextAdapter
import io.embrace.opentelemetry.kotlin.context.toOtelJavaContext
import io.embrace.opentelemetry.kotlin.tracing.conversion.toOtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanAdapter
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.model.SpanRelationships
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
