package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.attributes.AttributeContainer
import io.embrace.opentelemetry.kotlin.k2j.tracing.AttributeContainerImpl
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.model.Link
import io.embrace.opentelemetry.kotlin.tracing.model.ReadWriteSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableLink
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class FakeReadWriteSpan(
    override var name: String = "span",
    override var status: StatusCode = StatusCode.Ok,
    override val parent: SpanContext? = FakeSpanContext(),
    override val spanContext: SpanContext = FakeSpanContext(),
    override val spanKind: SpanKind = SpanKind.INTERNAL,
    override val startTimestamp: Long = 1000,
    override val endTimestamp: Long = 2000,
    override val resource: Resource = FakeResource(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo = FakeInstrumentationScopeInfo(),
    override val attributes: Map<String, Any> = mapOf("key" to "value"),
    override val events: List<ReadableSpanEvent> = listOf(FakeReadableSpanEvent()),
    override val links: List<ReadableLink> = listOf(FakeReadableLink()),
    private val attrs: AttributeContainer = AttributeContainerImpl()
) : ReadWriteSpan, AttributeContainer by attrs {

    override fun end() {
    }

    override fun end(timestamp: Long) {
    }

    override fun isRecording(): Boolean = true

    override fun addLink(spanContext: SpanContext, action: AttributeContainer.() -> Unit) {
    }

    override fun addEvent(name: String, timestamp: Long?, action: AttributeContainer.() -> Unit) {
    }

    override fun events(): List<SpanEvent> = emptyList()

    override fun links(): List<Link> = emptyList()

    override fun hasEnded(): Boolean = false
}
