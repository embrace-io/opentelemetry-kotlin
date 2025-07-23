package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteSpan
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.j2k.tracing.convertToOtelKotlin
import io.embrace.opentelemetry.kotlin.k2j.tracing.AttributeContainerImpl
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.tracing.LinkImpl
import io.embrace.opentelemetry.kotlin.tracing.SpanEventImpl
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.model.Link
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent
import io.opentelemetry.api.common.AttributeKey
import java.util.concurrent.TimeUnit

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class ReadWriteSpanAdapter(
    private val impl: OtelJavaReadWriteSpan,
    private val readableSpan: ReadableSpanAdapter = ReadableSpanAdapter(impl)
) : ReadWriteSpan, ReadableSpan by readableSpan {

    private val data = impl.toSpanData()

    override var name: String
        get() = impl.name
        set(value) {}

    override var status: StatusCode
        get() = data.status.convertToOtelKotlin()
        set(value) {}

    override fun end() {
        impl.end()
    }

    override fun end(timestamp: Long) {
        impl.end(timestamp, TimeUnit.NANOSECONDS)
    }

    override fun isRecording(): Boolean = impl.isRecording

    override fun addLink(spanContext: SpanContext, attributes: AttributeContainer.() -> Unit) {
        val container = AttributeContainerImpl()
        attributes(container)
        val ctx = (spanContext as SpanContextAdapter).impl
        impl.addLink(ctx, container.otelJavaAttributes())
    }

    override fun addEvent(name: String, timestamp: Long?, attributes: AttributeContainer.() -> Unit) {
        val container = AttributeContainerImpl()
        attributes(container)
        impl.addEvent(name, container.otelJavaAttributes(), timestamp ?: 0, TimeUnit.NANOSECONDS)
    }

    override fun events(): List<SpanEvent> {
        return data.events.map {
            val container = AttributeContainerImpl(it.attributes.toBuilder())
            SpanEventImpl(it.name, it.epochNanos, container)
        }
    }

    override fun links(): List<Link> {
        return data.links.map {
            val container = AttributeContainerImpl(it.attributes.toBuilder())
            LinkImpl(SpanContextAdapter(it.spanContext), container)
        }
    }

    override fun setBooleanAttribute(key: String, value: Boolean) {
        impl.setAttribute(key, value)
    }

    override fun setStringAttribute(key: String, value: String) {
        impl.setAttribute(key, value)
    }

    override fun setLongAttribute(key: String, value: Long) {
        impl.setAttribute(key, value)
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        impl.setAttribute(key, value)
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
        impl.setAttribute(AttributeKey.booleanArrayKey(key), value)
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        impl.setAttribute(AttributeKey.stringArrayKey(key), value)
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        impl.setAttribute(AttributeKey.longArrayKey(key), value)
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        impl.setAttribute(AttributeKey.doubleArrayKey(key), value)
    }

    override fun attributes(): Map<String, Any> = impl.attributes.toMap()
}
