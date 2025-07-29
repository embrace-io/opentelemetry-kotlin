package io.embrace.opentelemetry.kotlin.fakes.otel.kotlin

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.data.LinkData
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
internal class FakeLinkData(
    override val spanContext: SpanContext = FakeSpanContext(),
    override val attributes: Map<String, Any> = mapOf("key" to "value")
) : LinkData
