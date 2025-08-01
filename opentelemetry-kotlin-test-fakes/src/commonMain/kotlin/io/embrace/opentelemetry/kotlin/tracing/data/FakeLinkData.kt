package io.embrace.opentelemetry.kotlin.tracing.data

import io.embrace.opentelemetry.kotlin.ExperimentalApi
import io.embrace.opentelemetry.kotlin.tracing.FakeSpanContext
import io.embrace.opentelemetry.kotlin.tracing.model.SpanContext

@OptIn(ExperimentalApi::class)
class FakeLinkData(
    override val spanContext: SpanContext = FakeSpanContext(),
    override val attributes: Map<String, Any> = mapOf("key" to "value")
) : LinkData
