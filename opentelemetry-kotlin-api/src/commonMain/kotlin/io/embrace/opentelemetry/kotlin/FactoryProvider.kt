package io.embrace.opentelemetry.kotlin

import io.embrace.opentelemetry.kotlin.tracing.model.SpanContextFactory

@ExperimentalApi
public interface FactoryProvider {
    public val spanContextFactory: SpanContextFactory
}
