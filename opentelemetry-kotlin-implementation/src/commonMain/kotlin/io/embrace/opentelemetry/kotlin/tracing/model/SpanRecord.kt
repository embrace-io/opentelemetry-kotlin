package io.embrace.opentelemetry.kotlin.tracing.model

import io.embrace.opentelemetry.kotlin.Clock
import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.ReentrantReadWriteLock
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainerImpl
import io.embrace.opentelemetry.kotlin.context.Context
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.SpanEventImpl
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
    private val clock: Clock,
    private val processor: SpanProcessor,
    private val parentContext: Context,
    name: String,
    private val attrs: MutableAttributeContainer = MutableAttributeContainerImpl(),
    override val spanKind: SpanKind,
    override val startTimestamp: Long
) : ReadWriteSpan {

    private enum class State {
        STARTED,
        ENDING,
        ENDED
    }

    private val lock = ReentrantReadWriteLock()

    private var state: State = State.STARTED

    override var name: String = name
        get() = readSpan {
            field
        }
        set(value) {
            writeSpan {
                field = value
            }
        }

    override var status: StatusData = StatusData.Unset
        get() = readSpan {
            field
        }
        set(value) {
            writeSpan {
                field = value
            }
        }

    override fun end() {
        endInternal(clock.now())
    }

    override fun end(timestamp: Long) {
        endInternal(timestamp)
    }

    private fun endInternal(timestamp: Long) {
        writeSpan(
            condition = {
                state == State.STARTED
            },
            action = {
                state = State.ENDING
                endTimestamp = timestamp
                processor.onStart(ReadWriteSpanImpl(this), parentContext)
                state = State.ENDED
                processor.onEnd(ReadableSpanImpl(this))
            }
        )
    }

    override fun isRecording(): Boolean = state != State.ENDED

    override val parent: SpanContext
        get() = throw UnsupportedOperationException()

    override val spanContext: SpanContext
        get() = throw UnsupportedOperationException()

    private val eventsList = mutableListOf<EventData>()

    override val events: List<EventData>
        get() = readSpan { eventsList.toList() }

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
        writeSpan {
            val attrs = MutableAttributeContainerImpl().apply(attributes)
            val event = SpanEventImpl(name, timestamp ?: clock.now(), attrs)
            eventsList.add(event)
        }
    }

    override fun toSpanData(): SpanData {
        throw UnsupportedOperationException()
    }

    override var endTimestamp: Long? = null

    override val resource: Resource
        get() = throw UnsupportedOperationException()

    override val instrumentationScopeInfo: InstrumentationScopeInfo
        get() = throw UnsupportedOperationException()

    override val hasEnded: Boolean
        get() = state == State.ENDED

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

    private inline fun <T> writeSpan(
        crossinline condition: () -> Boolean = ::isRecording,
        crossinline action: () -> T,
    ) {
        return lock.write {
            if (condition()) {
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
