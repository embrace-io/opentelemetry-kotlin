package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import io.embrace.opentelemetry.kotlin.tracing.SpanRelationshipContainer
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.opentelemetry.context.Context
import java.util.concurrent.TimeUnit

internal class TracerAdapter(
    private val tracer: io.opentelemetry.api.trace.Tracer,
    private val clock: ClockAdapter
) : Tracer {

    override fun createSpan(
        name: String,
        parent: Span?,
        spanKind: SpanKind,
        startTimestamp: Long?,
        action: SpanRelationshipContainer.() -> Unit
    ): Span {
        val builder = tracer.spanBuilder(name)
            .setSpanKind(spanKind.convertToOtelJava())
            .setStartTimestamp(startTimestamp ?: clock.now(), TimeUnit.NANOSECONDS)

        val ctx = parent?.let { parent.findSpanContext() }
        if (ctx != null) {
            builder.setParent(ctx)
        }

        val span = builder.startSpan()
        return SpanAdapter(span, clock, parent?.spanContext).apply {
            this.name = name
            action(this)
        }
    }

    private fun Span.findSpanContext(): Context? {
        when (this) {
            is SpanAdapter -> {
                val ctx = Context.current() ?: Context.root()
                return ctx.with(impl)
            }

            else -> return null
        }
    }
}
