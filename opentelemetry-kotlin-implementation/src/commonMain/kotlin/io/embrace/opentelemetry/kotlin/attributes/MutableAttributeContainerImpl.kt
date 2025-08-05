package io.embrace.opentelemetry.kotlin.attributes

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.ThreadSafe
import io.embrace.opentelemetry.kotlin.threadSafeMap

@OptIn(ExperimentalApi::class)
@ThreadSafe
internal class MutableAttributeContainerImpl : MutableAttributeContainer {

    private val attrs: MutableMap<String, Any> = threadSafeMap()

    override fun setBooleanAttribute(key: String, value: Boolean) {
        attrs[key] = value
    }

    override fun setStringAttribute(key: String, value: String) {
        attrs[key] = value
    }

    override fun setLongAttribute(key: String, value: Long) {
        attrs[key] = value
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        attrs[key] = value
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
        attrs[key] = value
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
        attrs[key] = value
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
        attrs[key] = value
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
        attrs[key] = value
    }

    override val attributes: Map<String, Any>
        get() = attrs.toMap()
}
