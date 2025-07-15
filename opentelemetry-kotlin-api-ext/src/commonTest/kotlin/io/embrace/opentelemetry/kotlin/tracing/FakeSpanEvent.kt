package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent

@OptIn(ExperimentalApi::class)
internal class FakeSpanEvent(
    override val name: String,
    override val timestamp: Long
) : SpanEvent {

    private val attrs = mutableMapOf<String, Any>()

    override fun setStringAttribute(key: String, value: String) {
        attrs[key] = value
    }

    override fun setBooleanAttribute(key: String, value: Boolean) {
        attrs[key] = value
    }

    override fun setLongAttribute(key: String, value: Long) {
        attrs[key] = value
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        attrs[key] = value
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
        attrs[key] = value
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        attrs[key] = value
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        attrs[key] = value
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        attrs[key] = value
    }

    override fun attributes(): Map<String, Any> = attrs.toMap()
}
