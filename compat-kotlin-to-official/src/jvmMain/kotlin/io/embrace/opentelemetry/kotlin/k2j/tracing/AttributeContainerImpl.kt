package io.embrace.opentelemetry.kotlin.k2j.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributeKey
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaAttributes
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer

@OptIn(ExperimentalApi::class)
internal class AttributeContainerImpl : AttributeContainer {

    private val attrs = OtelJavaAttributes.builder()

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
        attrs.put(OtelJavaAttributeKey.booleanArrayKey(key), value)
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
        attrs.put(OtelJavaAttributeKey.stringArrayKey(key), value)
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
        attrs.put(OtelJavaAttributeKey.longArrayKey(key), value)
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
        attrs.put(OtelJavaAttributeKey.doubleArrayKey(key), value)
    }

    override fun attributes(): Map<String, Any> = attrs.build().toMap()

    fun otelJavaAttributes(): OtelJavaAttributes = attrs.build()
}
