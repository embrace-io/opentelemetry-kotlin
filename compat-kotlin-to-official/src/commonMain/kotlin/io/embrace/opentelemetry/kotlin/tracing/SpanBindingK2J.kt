package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.StatusCode
import java.util.concurrent.TimeUnit

internal class SpanBindingK2J(
    private val span: io.opentelemetry.api.trace.Span,
) : Span {

    override fun setBooleanAttribute(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override var name: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var status: StatusCode
        get() = TODO("Not yet implemented")
        set(value) {}
    override var spanKind: SpanKind
        get() = TODO("Not yet implemented")
        set(value) {}
    override var parent: SpanContext?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun updateStartTimestamp(timestamp: Long, unit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun end(timestamp: Long?, unit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override val isRecording: Boolean
        get() = TODO("Not yet implemented")

    override fun addLink(spanContext: SpanContext, action: Link.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, timestamp: Long?, action: SpanEvent.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun setStringAttribute(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setLongAttribute(key: String, value: Long) {
        TODO("Not yet implemented")
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        TODO("Not yet implemented")
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        TODO("Not yet implemented")
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
