package io.embrace.opentelemetry.kotlin.creator

import io.embrace.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
internal object NoopObjectCreator : ObjectCreator {
    override val spanContext: SpanContextCreator = NoopSpanContextCreator
}
