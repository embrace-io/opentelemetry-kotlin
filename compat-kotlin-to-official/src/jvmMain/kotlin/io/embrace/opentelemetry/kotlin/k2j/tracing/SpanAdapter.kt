package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.Link
import io.embrace.opentelemetry.kotlin.tracing.Span
import io.embrace.opentelemetry.kotlin.tracing.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.SpanEvent
import java.util.concurrent.TimeUnit

internal class SpanAdapter(
    private val impl: io.opentelemetry.api.trace.Span,
) : Span {

    override fun setBooleanAttribute(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

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

    override var parent: SpanContext?
        get() = TODO("Not yet implemented")
        set(value) {
            TODO("$value")
        }

    override fun end(timestamp: Long?) {
        if (timestamp != null) {
            impl.end(timestamp, TimeUnit.NANOSECONDS)
        } else {
            impl.end()
        }
    }

    override fun isRecording(): Boolean = impl.isRecording

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

    override fun attributes(): Map<String, Any> {
        TODO("Not yet implemented")
    }

    override fun events(): List<SpanEvent> {
        TODO("Not yet implemented")
    }

    override fun links(): List<Link> {
        TODO("Not yet implemented")
    }
}
