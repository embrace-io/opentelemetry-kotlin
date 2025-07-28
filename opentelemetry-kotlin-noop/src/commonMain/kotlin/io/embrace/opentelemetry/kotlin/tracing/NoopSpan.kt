package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.model.Link
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@ExperimentalApi
internal object NoopSpan : Span {

    override var name: String = ""
    override var status: StatusCode = StatusCode.Unset
    override val parent: SpanContext = NoopSpanContext
    override val spanContext: SpanContext = NoopSpanContext
    override val spanKind: SpanKind = SpanKind.INTERNAL
    override val startTimestamp: Long = -1L

    override fun end() {
    }

    override fun end(timestamp: Long) {
    }

    override fun addLink(spanContext: SpanContext, attributes: AttributeContainer.() -> Unit) {
    }

    override fun addEvent(name: String, timestamp: Long?, attributes: AttributeContainer.() -> Unit) {
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
