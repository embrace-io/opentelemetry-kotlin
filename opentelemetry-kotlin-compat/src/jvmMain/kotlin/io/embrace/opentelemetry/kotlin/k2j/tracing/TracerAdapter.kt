package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.k2j.context.ContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.context.toOtelJava
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.embrace.opentelemetry.kotlin.tracing.model.Span
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
            .setSpanKind(spanKind.toOtelJava())
            .setStartTimestamp(start, TimeUnit.NANOSECONDS)

        if (parentContext != null) {
            builder.setParent(parentContext.toOtelJava())
        } else {
            builder.setNoParent()
        }

        val span = builder.startSpan()
        return SpanAdapter(
            impl = span,
            clock = clock,
            parentCtx = parentContext?.let(::ContextAdapter) ?: OtelJavaContext.current() ?: OtelJavaContext.root(),
            spanKind = spanKind,
            startTimestamp = start
        ).apply {
            this.name = name
            action(this)
        }
    }
}
