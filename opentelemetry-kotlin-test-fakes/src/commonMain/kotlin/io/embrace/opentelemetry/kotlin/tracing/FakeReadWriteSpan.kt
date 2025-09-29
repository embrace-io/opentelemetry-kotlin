package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.FakeInstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.MutableAttributeContainer
import io.embrace.opentelemetry.kotlin.resource.FakeResource
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.data.EventData
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.data.SpanData
import io.embrace.opentelemetry.kotlin.tracing.data.StatusData
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
class FakeReadWriteSpan(
    override var name: String = "fake_span",
    override var status: StatusData = StatusData.Unset,
    override val parent: SpanContext = FakeSpanContext.INVALID,
    override val spanContext: SpanContext = FakeSpanContext.INVALID,
    override val spanKind: SpanKind = SpanKind.INTERNAL,
    override val startTimestamp: Long = 0,
    override val events: List<EventData> = emptyList(),
    override val links: List<LinkData> = emptyList(),
    override val attributes: Map<String, Any> = emptyMap(),
    override val endTimestamp: Long? = 0,
    override val resource: Resource = FakeResource(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo = FakeInstrumentationScopeInfo(),
    override val hasEnded: Boolean = false
) : ReadWriteSpan {

    override fun end() {
    }

    override fun end(timestamp: Long) {
    }

    override fun isRecording(): Boolean = true

    override fun addLink(
        spanContext: SpanContext,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        throw UnsupportedOperationException()
    }

    override fun addEvent(
        name: String,
        timestamp: Long?,
        attributes: (MutableAttributeContainer.() -> Unit)?
    ) {
        throw UnsupportedOperationException()
    }

    override fun setBooleanAttribute(key: String, value: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun setStringAttribute(key: String, value: String) {
        throw UnsupportedOperationException()
    }

    override fun setLongAttribute(key: String, value: Long) {
        throw UnsupportedOperationException()
    }

    override fun setDoubleAttribute(key: String, value: Double) {
        throw UnsupportedOperationException()
    }

    override fun setBooleanListAttribute(
        key: String,
        value: List<Boolean>
    ) {
        throw UnsupportedOperationException()
    }

    override fun setStringListAttribute(
        key: String,
        value: List<String>
    ) {
        throw UnsupportedOperationException()
    }

    override fun setLongListAttribute(
        key: String,
        value: List<Long>
    ) {
        throw UnsupportedOperationException()
    }

    override fun setDoubleListAttribute(
        key: String,
        value: List<Double>
    ) {
        throw UnsupportedOperationException()
    }

    override fun toSpanData(): SpanData {
        throw UnsupportedOperationException()
    }
}
