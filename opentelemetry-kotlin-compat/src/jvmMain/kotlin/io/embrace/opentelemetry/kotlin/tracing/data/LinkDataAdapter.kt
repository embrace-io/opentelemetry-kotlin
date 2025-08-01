package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.attributes.toMap
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextAdapter

@OptIn(ExperimentalApi::class)
internal class LinkDataAdapter(
    impl: OtelJavaLinkData,
) : LinkData {
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val attributes: Map<String, Any> = impl.attributes.toMap()
}
