package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.k2j.ClockAdapter
import io.embrace.opentelemetry.kotlin.tracing.Link
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanEvent
import io.opentelemetry.api.common.AttributeKey
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

internal class SpanAdapter(
    val impl: io.opentelemetry.api.trace.Span,
    private val clock: ClockAdapter,
    override val parent: SpanContext?,
) : Span {

    private val attrs: MutableMap<String, Any> = ConcurrentHashMap()
    private val events: ConcurrentLinkedQueue<SpanEvent> = ConcurrentLinkedQueue()
    private val links: ConcurrentLinkedQueue<Link> = ConcurrentLinkedQueue()

    private var implName: String = ""
    private var implStatus: StatusCode = StatusCode.Unset

    override var name: String
        get() = implName
        set(value) {
            implName = value
            impl.updateName(value)
        }

    override var status: StatusCode
        get() = implStatus
        set(value) {
            implStatus = value
            impl.setStatus(value.convertToOtelJava())
        }

    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)

    override fun end(timestamp: Long?) {
        if (timestamp != null) {
            impl.end(timestamp, TimeUnit.NANOSECONDS)
        } else {
            impl.end()
        }
    }

    override fun isRecording(): Boolean = impl.isRecording

    override fun addLink(spanContext: SpanContext, action: AttributeContainer.() -> Unit) {
        val container = AttributeContainerImpl()
        action(container)
        links.add(LinkImpl(spanContext, container))
        impl.addLink(spanContext.convertToOtelJava(), container.otelJavaAttributes())
    }

    override fun addEvent(name: String, timestamp: Long?, action: AttributeContainer.() -> Unit) {
        val container = AttributeContainerImpl()
        action(container)
        val time = timestamp ?: clock.now()
        events.add(SpanEventImpl(name, time, container))
        impl.addEvent(name, container.otelJavaAttributes(), time, TimeUnit.NANOSECONDS)
    }

    override fun events(): List<SpanEvent> = events.toList()

    override fun links(): List<Link> = links.toList()

    override fun setBooleanAttribute(key: String, value: Boolean) {
        impl.setAttribute(key, value)
        attrs[key] = value
    }

    override fun setStringAttribute(key: String, value: String) {
        impl.setAttribute(key, value)
        attrs[key] = value
    }

    override fun setLongAttribute(key: String, value: Long) {
        impl.setAttribute(key, value)
        attrs[key] = value
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        impl.setAttribute(key, value)
        attrs[key] = value
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
        impl.setAttribute(AttributeKey.booleanArrayKey(key), value)
        attrs[key] = value
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        impl.setAttribute(AttributeKey.stringArrayKey(key), value)
        attrs[key] = value
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        impl.setAttribute(AttributeKey.longArrayKey(key), value)
        attrs[key] = value
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        impl.setAttribute(AttributeKey.doubleArrayKey(key), value)
        attrs[key] = value
    }

    override fun attributes(): Map<String, Any> = attrs.toMap()
}
