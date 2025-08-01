package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaReadWriteSpan
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.opentelemetry.api.common.AttributeKey
import java.util.concurrent.TimeUnit

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class ReadWriteSpanAdapter(
    private val impl: OtelJavaReadWriteSpan,
    private val readableSpan: ReadableSpanAdapter = ReadableSpanAdapter(impl)
) : ReadWriteSpan, ReadableSpan by readableSpan {

    override var name: String
        get() = impl.name
        set(value) {}

    override var status: StatusData
        get() = readableSpan.status
        set(value) {}

    override val hasEnded: Boolean = impl.hasEnded()

    override fun end() {
        impl.end()
    }

    override fun end(timestamp: Long) {
        impl.end(timestamp, TimeUnit.NANOSECONDS)
    }

    override fun isRecording(): Boolean = impl.isRecording

    override fun addLink(spanContext: SpanContext, attributes: MutableAttributeContainer.() -> Unit) {
        val container = MutableAttributeContainerImpl()
        attributes(container)
        val ctx = (spanContext as SpanContextAdapter).impl
        impl.addLink(ctx, container.otelJavaAttributes())
    }

    override fun addEvent(name: String, timestamp: Long?, attributes: MutableAttributeContainer.() -> Unit) {
        val container = MutableAttributeContainerImpl()
        attributes(container)
        impl.addEvent(name, container.otelJavaAttributes(), timestamp ?: 0, TimeUnit.NANOSECONDS)
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
}
