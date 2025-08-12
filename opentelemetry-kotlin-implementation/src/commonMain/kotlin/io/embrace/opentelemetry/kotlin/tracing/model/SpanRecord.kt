package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.ReentrantReadWriteLock
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.export.SpanProcessor

/**
 * The single source of truth for span state. This is not exposed to consumers of the API - they
 * are presented with views such as [CreatedSpan], depending on which API call they make.
 */
@OptIn(ExperimentalApi::class)
internal class SpanRecord(
    @Suppress("unused") private val clock: Clock,
    @Suppress("unused") private val processor: SpanProcessor,
    private val attrs: MutableAttributeContainer = MutableAttributeContainerImpl()
) : ReadWriteSpan {

    private val lock = ReentrantReadWriteLock()

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

    override fun toSpanData(): SpanData {
        throw UnsupportedOperationException()
    }

    override val endTimestamp: Long?
        get() = throw UnsupportedOperationException()

    override val resource: Resource
        get() = throw UnsupportedOperationException()

    override val instrumentationScopeInfo: InstrumentationScopeInfo
        get() = throw UnsupportedOperationException()

    override val hasEnded: Boolean
        get() = throw UnsupportedOperationException()

    override val attributes: Map<String, Any>
        get() = readSpan {
            attrs.attributes
        }

    override fun setBooleanAttribute(key: String, value: Boolean) {
        writeSpan {
            attrs.setBooleanAttribute(key, value)
        }
    }

    override fun setStringAttribute(key: String, value: String) {
        writeSpan {
            attrs.setStringAttribute(key, value)
        }
    }

    override fun setLongAttribute(key: String, value: Long) {
        writeSpan {
            attrs.setLongAttribute(key, value)
        }
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        writeSpan {
            attrs.setDoubleAttribute(key, value)
        }
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
        writeSpan {
            attrs.setBooleanListAttribute(key, value)
        }
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
        writeSpan {
            attrs.setStringListAttribute(key, value)
        }
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
        writeSpan {
            attrs.setLongListAttribute(key, value)
        }
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
        writeSpan {
            attrs.setDoubleListAttribute(key, value)
        }
    }

    private inline fun <T> writeSpan(crossinline action: () -> T) {
        return lock.write {
            if (isRecording()) {
                action()
            }
        }
    }

    private inline fun <T> readSpan(crossinline action: () -> T): T {
        return lock.read {
            action()
        }
    }
}
