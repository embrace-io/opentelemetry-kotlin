package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaSpan
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.Link
import io.embrace.opentelemetry.kotlin.tracing.LinkImpl
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanEvent
import io.embrace.opentelemetry.kotlin.tracing.SpanEventImpl
import io.embrace.opentelemetry.kotlin.tracing.SpanKind
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalApi::class)
public class SpanAdapter( // temporarily public, will be internal in future
    public val impl: OtelJavaSpan,
    private val clock: Clock,
    override val parent: SpanContext?,
    override val spanKind: SpanKind,
    override val startTimestamp: Long,
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

    override fun end() {
        impl.end()
    }

    override fun end(timestamp: Long) {
        impl.end(timestamp, TimeUnit.NANOSECONDS)
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
        impl.setAttribute(OtelJavaAttributeKey.booleanArrayKey(key), value)
        attrs[key] = value
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        impl.setAttribute(OtelJavaAttributeKey.stringArrayKey(key), value)
        attrs[key] = value
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        impl.setAttribute(OtelJavaAttributeKey.longArrayKey(key), value)
        attrs[key] = value
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        impl.setAttribute(OtelJavaAttributeKey.doubleArrayKey(key), value)
        attrs[key] = value
    }

    override fun attributes(): Map<String, Any> = attrs.toMap()
}
