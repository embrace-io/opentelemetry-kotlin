package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes

internal class AttributeContainerImpl : AttributeContainer {

    private val attrs = Attributes.builder()

    override fun setBooleanAttribute(key: String, value: Boolean) {
        attrs.put(key, value)
    }

    override fun setStringAttribute(key: String, value: String) {
        attrs.put(key, value)
    }

    override fun setLongAttribute(key: String, value: Long) {
        attrs.put(key, value)
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        attrs.put(key, value)
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
        attrs.put(AttributeKey.booleanArrayKey(key), value)
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        attrs.put(AttributeKey.stringArrayKey(key), value)
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        attrs.put(AttributeKey.longArrayKey(key), value)
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        attrs.put(AttributeKey.doubleArrayKey(key), value)
    }

    override fun attributes(): Map<String, Any> = attrs.build().toMap()

    fun otelJavaAttributes(): Attributes = attrs.build()
}
