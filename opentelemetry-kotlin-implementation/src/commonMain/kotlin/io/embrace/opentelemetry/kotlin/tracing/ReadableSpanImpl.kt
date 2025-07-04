package io.embrace.opentelemetry.kotlin.tracing

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.InstrumentationScopeInfo
import io.embrace.opentelemetry.kotlin.resource.Resource
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableLink
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpan
import io.embrace.opentelemetry.kotlin.tracing.model.ReadableSpanEvent
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanKind

@OptIn(ExperimentalApi::class)
public class ReadableSpanImpl(
    override val name: String,
    override val status: StatusCode,
    override val parent: SpanContext?,
    override val spanContext: SpanContext,
    override val spanKind: SpanKind,
    override val startTimestamp: Long,
    override val endTimestamp: Long?,
    override val resource: Resource,
    override val instrumentationScopeInfo: InstrumentationScopeInfo,
    override val attributes: Map<String, Any>,
    override val events: List<ReadableSpanEvent>,
    override val links: List<ReadableLink>,
    private val ended: () -> Boolean
) : ReadableSpan {

    override fun hasEnded(): Boolean = ended()
}
