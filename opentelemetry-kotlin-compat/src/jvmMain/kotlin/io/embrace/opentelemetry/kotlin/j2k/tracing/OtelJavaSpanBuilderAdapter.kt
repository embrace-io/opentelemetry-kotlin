package io.embrace.opentelemetry.kotlin.j2k.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributesBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanBuilder
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanContext
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpanKind
import io.embrace.opentelemetry.kotlin.attributes.setAttributes
import io.embrace.opentelemetry.kotlin.j2k.bridge.attrsFromMap
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.tracing.Tracer
import io.opentelemetry.context.Context
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
internal class OtelJavaSpanBuilderAdapter(
    private val tracer: Tracer,
    private val spanName: String
) : OtelJavaSpanBuilder {

    private var start: Long? = null
    private var parent: OtelJavaContext? = null
    private var kind: OtelJavaSpanKind = OtelJavaSpanKind.INTERNAL
    private val attrs: OtelJavaAttributesBuilder = OtelJavaAttributes.builder()
    private val links: MutableList<LinkBuilder> = mutableListOf()

    override fun setParent(context: OtelJavaContext): OtelJavaSpanBuilder {
        parent = context
        return this
    }

    override fun setNoParent(): OtelJavaSpanBuilder {
        parent = Context.root()
        return this
    }

    override fun addLink(spanContext: OtelJavaSpanContext): OtelJavaSpanBuilder {
        links.add(LinkBuilder(spanContext, OtelJavaAttributes.empty()))
        return this
    }

    override fun addLink(
        spanContext: OtelJavaSpanContext,
        attributes: OtelJavaAttributes
    ): OtelJavaSpanBuilder {
        links.add(LinkBuilder(spanContext, attributes))
        return this
    }

    override fun setAttribute(key: String, value: String): OtelJavaSpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Long): OtelJavaSpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Double): OtelJavaSpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setAttribute(key: String, value: Boolean): OtelJavaSpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun <T : Any> setAttribute(
        key: OtelJavaAttributeKey<T>,
        value: T
    ): OtelJavaSpanBuilder {
        attrs.put(key, value)
        return this
    }

    override fun setSpanKind(spanKind: OtelJavaSpanKind): OtelJavaSpanBuilder {
        kind = spanKind
        return this
    }

    override fun setStartTimestamp(startTimestamp: Long, unit: TimeUnit): OtelJavaSpanBuilder {
        start = startTimestamp
        return this
    }

    override fun startSpan(): OtelJavaSpan {
        val span = tracer.createSpan(
            name = spanName,
            spanKind = kind.convertToOtelKotlin(),
            startTimestamp = start,
            parent = findParentSpanContext()
        ) {
            setAttributes(attrs.build().asMap().mapKeys { it.key.key })
            links.forEach {
                addLink(it.spanContext, attrsFromMap(it.attributes.toMap()))
            }
        }
        return OtelJavaSpanAdapter(span)
    }

    private fun findParentSpanContext(): SpanContextAdapter {
        val ctx = parent ?: Context.current()
        val parentSpan = OtelJavaSpan.fromContext(ctx)
        return SpanContextAdapter(parentSpan.spanContext)
    }

    private class LinkBuilder(
        val spanContext: OtelJavaSpanContext,
        val attributes: OtelJavaAttributes
    )
}
