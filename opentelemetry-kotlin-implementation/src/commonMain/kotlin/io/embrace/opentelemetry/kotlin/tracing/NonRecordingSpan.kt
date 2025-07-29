package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.Link
import io.embrace.opentelemetry.kotlin.tracing.model.Span
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

/**
 * A reference to a [Span] that cannot actively record any data. This can be useful for
 * propagating traces where it's not necessary to mutate the span - e.g. if a caller only needs to
 * know the trace/span IDs for a parent span.
 */
@OptIn(ExperimentalApi::class)
public class NonRecordingSpan(
    override val parent: SpanContext,
    override val spanContext: SpanContext,
) : Span {

    override var name: String = ""
    override var status: StatusData = StatusData.Unset
    override val spanKind: SpanKind = SpanKind.INTERNAL
    override val startTimestamp: Long = 0

    override fun setBooleanAttribute(key: String, value: Boolean) {
    }

    override fun end() {
    }

    override fun end(timestamp: Long) {
    }

    override fun isRecording(): Boolean = false

    override fun addLink(spanContext: SpanContext, attributes: AttributeContainer.() -> Unit) {
    }

    override fun addEvent(
        name: String,
        timestamp: Long?,
        attributes: AttributeContainer.() -> Unit
    ) {
    }

    override fun events(): List<SpanEvent> = emptyList()

    override fun links(): List<Link> = emptyList()

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
