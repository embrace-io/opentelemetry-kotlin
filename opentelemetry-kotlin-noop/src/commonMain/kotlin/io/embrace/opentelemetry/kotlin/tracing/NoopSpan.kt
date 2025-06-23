package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.StatusCode
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@ExperimentalApi
internal object NoopSpan : Span {

    override var name: String = ""
    override var status: StatusCode = StatusCode.Unset
    override val parent: SpanContext? = null
    override val spanContext: SpanContext = NoopSpanContext
    override val spanKind: SpanKind = SpanKind.INTERNAL
    override val startTimestamp: Long = -1L

    override fun end() {
    }

    override fun end(timestamp: Long) {
    }

    override fun addLink(spanContext: SpanContext, action: AttributeContainer.() -> Unit) {
    }

    override fun addEvent(name: String, timestamp: Long?, action: AttributeContainer.() -> Unit) {
    }

    override fun isRecording(): Boolean = false
    override fun events(): List<SpanEvent> = emptyList()
    override fun links(): List<Link> = emptyList()

    override fun setBooleanAttribute(key: String, value: Boolean) {
    }

    override fun setStringAttribute(key: String, value: String) {
    }

    override fun setLongAttribute(key: String, value: Long) {
    }

    override fun setDoubleAttribute(key: String, value: Double) {
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
    }

    override fun attributes(): Map<String, Any> = emptyMap()
}
