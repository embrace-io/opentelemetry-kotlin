package io.embrace.opentelemetry.kotlin.j2k.tracing.export

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.aliases.OtelJavaLinkData
import io.embrace.opentelemetry.kotlin.k2j.tracing.SpanContextAdapter
import io.embrace.opentelemetry.kotlin.k2j.tracing.toMap
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class LinkDataAdapter(
    impl: OtelJavaLinkData
) : LinkData {
    override val spanContext: SpanContext = SpanContextAdapter(impl.spanContext)
    override val attributes: Map<String, Any> = impl.attributes.toMap()
}
