package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaTracer
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.SpanRelationships
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class TracerAdapter(
    private val tracer: OtelJavaTracer,
    private val clock: ClockAdapter
) : Tracer {

    override fun createSpan(
        name: String,
        parent: SpanContext?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationships.() -> Unit
    ): Span {
        val builder = tracer.spanBuilder(name)
            .setSpanKind(spanKind.convertToOtelJava())
            .setStartTimestamp(startTimestamp ?: clock.now(), TimeUnit.NANOSECONDS)

        if (parent != null) {
            val ctx = findContext(parent)
            builder.setParent(ctx)
        }

        val span = builder.startSpan()
        return SpanAdapter(span, clock, parent).apply {
            this.name = name
            action(this)
        }
    }

    private fun findContext(spanContext: SpanContext): OtelJavaContext {
        val ctx = OtelJavaContext.current() ?: OtelJavaContext.root()
        val impl = (spanContext as SpanContextAdapter).impl
        val span = OtelJavaSpan.wrap(impl)
        return ctx.with(span)
    }
}
