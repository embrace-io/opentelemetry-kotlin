package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.doubleCheckedLockSync
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData

@OptIn(ExperimentalApi::class)
internal class SpanImpl(
    private val attrs: MutableAttributeContainer = MutableAttributeContainerImpl()
) : Span {

    private val lock = Any()

    // in future this may need implementing as a tri-state enum to better support span processors
    private var recording = true

    override var name: String = ""
        get() = throw UnsupportedOperationException()

    override var status: StatusData = StatusData.Unset
        get() = throw UnsupportedOperationException()

    override fun end() {
        recording = false
    }

    override fun end(timestamp: Long) {
        throw UnsupportedOperationException()
    }

    override fun isRecording(): Boolean = recording

    override val parent: SpanContext
        get() = throw UnsupportedOperationException()

    override val spanContext: SpanContext
        get() = throw UnsupportedOperationException()

    override val spanKind: SpanKind
        get() = throw UnsupportedOperationException()

    override val startTimestamp: Long
        get() = throw UnsupportedOperationException()

    override val events: List<EventData>
        get() = throw UnsupportedOperationException()

    override val links: List<LinkData>
        get() = throw UnsupportedOperationException()

    override fun addLink(
        spanContext: SpanContext,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        throw UnsupportedOperationException()
    }

    override fun addEvent(
        name: String,
        timestamp: Long?,
        attributes: MutableAttributeContainer.() -> Unit
    ) {
        throw UnsupportedOperationException()
    }

    override val attributes: Map<String, Any>
        get() = attrs.attributes

    override fun setBooleanAttribute(key: String, value: Boolean) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setBooleanAttribute(key, value)
        }
    }

    override fun setStringAttribute(key: String, value: String) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setStringAttribute(key, value)
        }
    }

    override fun setLongAttribute(key: String, value: Long) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setLongAttribute(key, value)
        }
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setDoubleAttribute(key, value)
        }
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setBooleanListAttribute(key, value)
        }
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setStringListAttribute(key, value)
        }
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setLongListAttribute(key, value)
        }
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
        doubleCheckedLockSync(lock, ::isRecording) {
            attrs.setDoubleListAttribute(key, value)
        }
    }
}
