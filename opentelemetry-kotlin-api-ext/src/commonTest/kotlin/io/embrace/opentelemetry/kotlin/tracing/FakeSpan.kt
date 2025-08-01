package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.Link
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalApi::class)
internal class FakeSpan : Span {

    private val events = mutableListOf<SpanEvent>()

    override fun setBooleanAttribute(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override var name: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var status: StatusData
        get() = TODO("Not yet implemented")
        set(value) {}
    override val parent: SpanContext
        get() = TODO("Not yet implemented")
    override val spanContext: SpanContext
        get() = TODO("Not yet implemented")
    override val spanKind: SpanKind
        get() = TODO("Not yet implemented")
    override val startTimestamp: Long
        get() = TODO("Not yet implemented")

    override fun end() {
        TODO("Not yet implemented")
    }

    override fun end(timestamp: Long) {
        TODO("Not yet implemented")
    }

    override fun isRecording(): Boolean {
        TODO("Not yet implemented")
    }

    override fun addLink(spanContext: SpanContext, attributes: AttributeContainer.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addEvent(name: String, timestamp: Long?, attributes: AttributeContainer.() -> Unit) {
        events.add(FakeSpanEvent(name, timestamp ?: 0).apply(attributes))
    }

    override fun events(): List<SpanEvent> = events.toList()

    override fun links(): List<Link> {
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
}
