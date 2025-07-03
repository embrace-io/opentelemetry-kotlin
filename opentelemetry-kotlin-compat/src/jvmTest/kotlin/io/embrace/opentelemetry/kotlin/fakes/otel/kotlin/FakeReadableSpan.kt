package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.StatusCode
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableLink
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
internal class FakeReadableSpan(
    override val name: String = "span",
    override val status: StatusCode = StatusCode.Ok,
    override val parent: SpanContext? = FakeSpanContext(),
    override val spanContext: SpanContext = FakeSpanContext(),
    override val spanKind: SpanKind = SpanKind.INTERNAL,
    override val startTimestamp: Long = 1000,
    override val resource: Resource = FakeResource(),
    override val instrumentationScopeInfo: InstrumentationScopeInfo = FakeInstrumentationScopeInfo(),
    override val attributes: Map<String, Any> = mapOf("key" to "value"),
    override val events: List<ReadableSpanEvent> = listOf(FakeReadableSpanEvent()),
    override val links: List<ReadableLink> = listOf(FakeReadableLink())
) : ReadableSpan {

    override fun hasEnded(): Boolean = false
}
