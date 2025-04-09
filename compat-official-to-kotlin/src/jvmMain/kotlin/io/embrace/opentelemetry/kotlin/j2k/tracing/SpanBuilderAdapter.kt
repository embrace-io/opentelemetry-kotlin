package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.setAttributes
import io.embrace.opentelemetry.kotlin.j2k.OtelKotlinTracer
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.common.AttributesBuilder
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanBuilder
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.context.Context
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class SpanBuilderAdapter(
    private val tracer: OtelKotlinTracer,
    private val spanName: String
) : SpanBuilder {

    private var start: Long? = null
    private var parent: Context? = null // TODO: pass parent context to createSpan
    private var kind: SpanKind = SpanKind.INTERNAL
    private val attrs: AttributesBuilder = Attributes.builder()

    override fun setParent(context: Context): SpanBuilder {
        parent = context
        return this
    }

    override fun setNoParent(): SpanBuilder {
        parent = null
        return this
    }

    override fun addLink(spanContext: SpanContext): SpanBuilder {
        TODO("Not yet implemented")
    }

    override fun addLink(spanContext: SpanContext, attributes: Attributes): SpanBuilder {
        TODO("Not yet implemented")
    }

    override fun setAttribute(key: String, value: String): SpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Long): SpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Double): SpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Boolean): SpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun <T : Any?> setAttribute(key: AttributeKey<T>, value: T): SpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setSpanKind(spanKind: SpanKind): SpanBuilder {
        kind = spanKind
        return this
    }

    override fun setStartTimestamp(startTimestamp: Long, unit: TimeUnit): SpanBuilder {
        start = startTimestamp
        return this
    }

    override fun startSpan(): Span {
        val span = tracer.createSpan(
            name = spanName,
            spanKind = kind.convertToOtelKotlin(),
            startTimestamp = start
        ) {
            setAttributes(attrs.build().asMap().mapKeys { it.key.key })
        }
        return SpanAdapter(span)
    }
}
